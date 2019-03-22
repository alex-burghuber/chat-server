package messages;

import java.io.Serializable;

public class Message implements Serializable {

    public String type;

    public Message(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
