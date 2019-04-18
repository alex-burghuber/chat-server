package messages;

public class ChatMessage extends Message {

    private String sender;
    private String receiver;
    private String kind;
    private long time;
    private String content;

    public ChatMessage(String type, String sender, String receiver, String kind, long time, String content) {
        super(type);
        this.sender = sender;
        this.receiver = receiver;
        this.kind = kind;
        this.time = time;
        this.content = content;
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
