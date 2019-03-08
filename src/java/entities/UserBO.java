package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserBO {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    @OneToMany
    private List<UserBO> followers;

    public UserBO() {
        this.followers = new ArrayList<>();
    }

    public UserBO(String username) {
        this();
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserBO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserBO> followers) {
        this.followers = followers;
    }
}
