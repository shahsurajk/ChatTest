package in.madscientist.chattest.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by frapp on 11/2/17.
 */

public class Message  extends User{

    private String body;

    private int isFavourite=0;

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    @JsonProperty("message-time")
    private String message_time;

    public String getBody() {
        return body;
    }

    public User getUser()
    {
        return new User(getName(),getImage_url(),getUsername());
    }
    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }
}
