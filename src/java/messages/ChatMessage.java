package messages;

public class ChatMessage extends Message {

    private String target;
    private String name;
    private String content;

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
