package dataprocessing;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CustomDateDeserializer extends JsonDeserializer<String> {
    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");


    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateInString=jsonParser.getText();

        if (dateInString.length()!=24){
            throw new IllegalArgumentException("Invalid timestamp length");
        }

        try{
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = simpleDateFormat.parse(dateInString);

            return simpleDateFormat.format(date);
        } catch (ParseException p){
            throw new RuntimeException(p);
        }

    }
}
