package entities;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class User {

    private Session session;
    private String username;

    private List<User> follower;

    public User(Session session, String username) {
        this.session = session;
        this.username = username;
        this.follower = new ArrayList<>();
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

    public List<User> getFollower() {
        return follower;
    }

    public void setFollower(List<User> follower) {
        this.follower = follower;
    }
}
