package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Chat_Group")
@NamedQueries({
        @NamedQuery(name = "Group.get-with-name", query = "SELECT g FROM GroupBO g WHERE g.name = :name"),
        @NamedQuery(name = "Group.count-name", query = "SELECT COUNT(g) FROM GroupBO g WHERE g.name = :name")
})
public class GroupBO {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private List<UserBO> members;

    public GroupBO() {
        this.members = new ArrayList<>();
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

    public List<UserBO> getMembers() {
        return members;
    }

    public void setMembers(List<UserBO> members) {
        this.members = members;
    }
}
