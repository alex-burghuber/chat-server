package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ChatServer_Group")
@NamedQueries({
        @NamedQuery(name = "Group.get-with-name", query = "SELECT g FROM GroupBO g WHERE g.name = :name")
})
public class GroupBO {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToMany
    private List<UserBO> users;

    public GroupBO() {
        this.users = new ArrayList<>();
    }

    public GroupBO(String name) {
        this();
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserBO> getUsers() {
        return users;
    }

    public void setUsers(List<UserBO> users) {
        this.users = users;
    }

}
