package dataprocessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class MessageController {

    private SimpMessagingTemplate template;

    private final MessageRepository repository;

    @Autowired
    public MessageController(MessageRepository repository, SimpMessagingTemplate template){
        this.repository=repository;
        this.template=template;
    }

    @PostMapping("/message")
    public Message sendMessage(@RequestBody @Valid Message message) throws Exception {
        Message msg = repository.save(message);
        template.convertAndSend("/topic/messages",msg);
        return msg;
    }

    @GetMapping(value = "/messages", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> messages(@PageableDefault(value=5) Pageable pageable, PagedResourcesAssembler assembler) {

        Page<Message> messages = repository.findAll(pageable);
        Page<Palindrome> palindromes = messages.map(this::convertToPalindrome);
        return new ResponseEntity(assembler.toResource(palindromes), HttpStatus.OK);
    }

    private Palindrome convertToPalindrome(Message msg){
        Manacher manacher= new Manacher(msg.getContent());
        return new Palindrome(msg,manacher.maxValue);

    }

}
