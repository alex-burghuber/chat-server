package messages;

import java.io.Serializable;

public class StatusMessage extends Message implements Serializable {

    public boolean isSuccess;
    public String kind;
    public String content;

    public StatusMessage(String type) {
        super(type);
    }

    public StatusMessage(String type, boolean isSuccess, String kind, String content) {
        super(type);
        this.isSuccess = isSuccess;
        this.kind = kind;
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
