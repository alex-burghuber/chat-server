package messages;

import java.io.Serializable;

public class ChatMessage extends Message implements Serializable {

    public String target;
    public String name;
    public String content;

    public ChatMessage(String type, String target, String name, String content) {
        super(type);
        this.target = target;
        this.name = name;
        this.content = content;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
