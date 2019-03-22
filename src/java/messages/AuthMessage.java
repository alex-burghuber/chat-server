package messages;

import java.io.Serializable;

public class AuthMessage extends Message implements Serializable {

    public String action;
    public String username;
    public String password;

    public AuthMessage(String type, String action, String username, String password) {
        super(type);
        this.action = action;
        this.username = username;
        this.password = password;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
