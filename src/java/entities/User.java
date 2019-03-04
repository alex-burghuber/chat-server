package entities;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class User {

    private Session session;
    private String username;

    private List<User> followers;

    public User(Session session, String username) {
        this.session = session;
        this.username = username;
        this.followers = new ArrayList<>();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
}
