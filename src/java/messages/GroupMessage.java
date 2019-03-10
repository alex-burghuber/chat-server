package messages;

public class GroupMessage extends Message {

    private String action;
    private String name;

    public GroupMessage(String type, String action, String name) {
        super(type);
        this.action = action;
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
