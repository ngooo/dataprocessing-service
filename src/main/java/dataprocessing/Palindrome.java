package dataprocessing;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Palindrome {

    @JsonUnwrapped
    private Message message;
    private int longest_palindrome_size;

    public Palindrome(Message message, int longest_palindrome_size){
        this.message=message;
        this.longest_palindrome_size=longest_palindrome_size;
    }

    public Message getMessage() {
        return message;
    }

    public int getLongest_palindrome_size() {
        return longest_palindrome_size;
    }
}
