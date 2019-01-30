package dataprocessing;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.*;

@ActiveProfiles("test")
public class MessageValidationTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void contentIsEmpty(){
        Message msg = new Message("", "2018-10-09 00:12:12+0100");
        Set<ConstraintViolation<Message>> constraintViolations = validator.validate(msg);
        assertEquals( 1, constraintViolations.size() );
        assertEquals("length must be between 1 and 2147483647",
                constraintViolations.iterator().next().getMessage()
        );

    }

    @Test
    public void contentIsNull(){
        Message msg = new Message(null, "2018-10-09 00:12:12+0100");
        Set<ConstraintViolation<Message>> constraintViolations = validator.validate(msg);
        assertEquals( 1, constraintViolations.size() );
        assertEquals("must not be null",
                constraintViolations.iterator().next().getMessage()
        );

    }

    @Test
    public void timeStampIsNull(){
        Message msg = new Message("abrakadabra", null);
        Set<ConstraintViolation<Message>> constraintViolations = validator.validate(msg);
        assertEquals( 1, constraintViolations.size() );
        assertEquals("must not be null",
                constraintViolations.iterator().next().getMessage()
        );

    }
}
