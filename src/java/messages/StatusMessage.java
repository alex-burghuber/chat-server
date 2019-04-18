package messages;

public class StatusMessage extends Message {

    private String kind;
    private boolean success;
    private String content;

    public StatusMessage(String type, String kind, boolean success, String content) {
        super(type);
        this.kind = kind;
        this.success = success;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
