package in.madscientist.chattest.model.pojo;

import java.util.List;

/**
 * Created by frapp on 11/2/17.
 */

public class Chats {
    private List<Message>messages;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
