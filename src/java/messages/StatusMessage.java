package messages;

public class StatusMessage extends Message {

    private String kind;
    private boolean isSuccess;
    private String content;

    public StatusMessage(String type, String kind) {
        super(type);
        this.kind = kind;
    }

    public StatusMessage(String type, String kind, boolean isSuccess, String content) {
        super(type);
        this.kind = kind;
        this.isSuccess = isSuccess;
        this.content = content;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
