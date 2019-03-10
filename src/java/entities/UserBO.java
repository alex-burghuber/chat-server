package entities;

import javax.persistence.*;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Chat_User")
@NamedQueries({
        @NamedQuery(name = "User.get-with-username", query = "SELECT u FROM UserBO u WHERE u.username = :username")
})
public class UserBO {

    @Id
    @GeneratedValue
    private long id;

    @Transient
    private Session session;

    @Column(unique = true)
    private String username;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<GroupBO> groups;

    public UserBO() {
        this.groups = new ArrayList<>();
    }

    public UserBO(Session session, String username) {
        this();
        this.session = session;
        this.username = username;
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

    public List<GroupBO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupBO> groups) {
        this.groups = groups;
    }
}
