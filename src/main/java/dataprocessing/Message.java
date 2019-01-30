package dataprocessing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MESSAGE")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(min=1)
    private String content;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @NotNull
    private String timestamp;

    public Message(){

    }

    public Message(String content, String timestamp){
        this.content=content;
        this.timestamp=timestamp;
    }

    public String getContent(){
        return content;
    }

    public String getTimestamp(){
        return timestamp;
    }
}
