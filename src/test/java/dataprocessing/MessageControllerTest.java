package dataprocessing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testSendAndGetMessages() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\",\"timestamp\": \"2018-10-09 00:12:12+0100\"}"))
                .andExpect(status().isOk());

        this.mvc.perform(get("/messages").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.palindromeList",hasSize(1)))
                .andExpect(jsonPath("$._embedded.palindromeList.[0].content",is("abrakadabra")))
                .andExpect(jsonPath("$._embedded.palindromeList.[0].timestamp", is("2018-10-08 23:12:12+0000")))
                .andExpect(jsonPath("$._embedded.palindromeList.[0].longest_palindrome_size", is(3)));

        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"ibrakadabra\",\"timestamp\": \"2018-10-09 00:12:12+0100\"}"))
                .andExpect(status().isOk());

        this.mvc.perform(get("/messages").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.palindromeList",hasSize(2)))
                .andExpect(jsonPath("$._embedded.palindromeList.[1].content",is("ibrakadabra")))
                .andExpect(jsonPath("$._embedded.palindromeList.[1].timestamp", is("2018-10-08 23:12:12+0000")))
                .andExpect(jsonPath("$._embedded.palindromeList.[1].longest_palindrome_size", is(3)));

        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"ab1ba aaaaaa\",\"timestamp\": \"2018-10-09 00:12:12+0100\"}"))
                .andExpect(status().isOk());

        this.mvc.perform(get("/messages").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.palindromeList",hasSize(3)))
                .andExpect(jsonPath("$._embedded.palindromeList.[2].content",is("ab1ba aaaaaa")))
                .andExpect(jsonPath("$._embedded.palindromeList.[2].timestamp", is("2018-10-08 23:12:12+0000")))
                .andExpect(jsonPath("$._embedded.palindromeList.[2].longest_palindrome_size", is(7)));
    }
    @Test
    public void testSendWrongTimestampFormat() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\",\"timestamp\": \"2018 00:12:12+0100\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testSendEmptyContent() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"\",\"timestamp\": \"2018-10-08 23:12:12+0000\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSendInvalidStructure() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\",\"timestamp\": \"2018-10-08 23:12:12+0000\",\"random\": \"random\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSendInvalidStructure2() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSendInvalidStructure3() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"timestamp\": \"2018-10-09 00:12:12+0100\"}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testSendInvalidStructureName() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"contentt\": \"abrakadabra\",\"timestamp\": \"2018-10-09 00:12:12+0100\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSendInvalidStructureName2() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\",\"timestampp\": \"2018-10-09 00:12:12+0100\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSendInvalidTimestampLength() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\",\"timestamp\": \"2018-10-9 00:12:12+0100\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testSendInvalidTimestampLength2() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\",\"timestamp\": \"2018-10--09 00:12:12+0100\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testSendInvalidTimestampLength3() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\",\"timestamp\": \"2018-10-09 00:12:12 +0100\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSendContentNull() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": null,\"timestamp\": \"2018-10-09 00:12:12+0100\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSendTimestampNull() throws Exception {
        this.mvc.perform((post("/message").contentType(MediaType.APPLICATION_JSON))
                .content("{\"content\": \"abrakadabra\",\"timestamp\": null}"))
                .andExpect(status().isBadRequest());
    }
}
