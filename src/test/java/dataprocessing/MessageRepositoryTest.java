package dataprocessing;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testFindAllMessages(){
        Message msg1 = new Message("abrakadabra", "2018-10-09 00:12:12+0100");
        Message msg2 = new Message("ibrakadabra", "2018-10-08 00:12:12+0100");

        entityManager.persist(msg1);
        entityManager.persist(msg2);
        entityManager.flush();
        List<Message> messages = new ArrayList<>();
        messages.add(msg1);
        messages.add(msg2);

        List<Message> messagesFromDatabase = messageRepository.findAll();
        assertEquals(messages,messagesFromDatabase);

    }
}
