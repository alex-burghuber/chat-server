package messages;

import java.io.Serializable;

public class GroupMessage extends Message implements Serializable {

    public String action;
    public String name;

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
