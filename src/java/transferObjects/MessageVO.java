package transferObjects;

public class MessageVO {

    private String to;
    private String content;

    public MessageVO(String to, String content) {
        this.to = to;
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
