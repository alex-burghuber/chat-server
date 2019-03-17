package entities;

import javax.persistence.*;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Chat_User")
@NamedQueries({
        @NamedQuery(name = "User.get-with-username", query = "SELECT u FROM UserBO u WHERE u.username = :username"),
        @NamedQuery(name = "User.count-username", query = "SELECT COUNT(u) FROM UserBO u WHERE u.username = :username")
})
public class UserBO {

    @Id
    @GeneratedValue
    private long id;

    @Transient
    private Session session;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "members", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private List<GroupBO> groups;

    public UserBO() {
        this.groups = new ArrayList<>();
    }

    public UserBO(Session session, String username, String password) {
        this();
        this.session = session;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GroupBO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupBO> groups) {
        this.groups = groups;
    }
}
