package entities;

import enums.ReceiverType;

import javax.persistence.*;

@Entity
@Table(name = "ChatServer_Message")
@NamedQueries({
        @NamedQuery(name = "Message.sender-receiver-ordered", query =
                "SELECT m FROM MessageBO m " +
                        "WHERE m.sender = :sender " +
                        "OR (m.receiverName = :receiverName AND m.receiverType = :receiverType)")
})
public class MessageBO {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private UserBO sender;

    private String receiverName;

    @Enumerated(value = EnumType.STRING)
    private ReceiverType receiverType;

    private long time;

    private String content;

    public MessageBO() {
    }

    public MessageBO(UserBO sender, String receiverName, ReceiverType receiverType, long time, String content) {
        this.sender = sender;
        this.receiverName = receiverName;
        this.receiverType = receiverType;
        this.time = time;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public UserBO getSender() {
        return sender;
    }

    public void setSender(UserBO sender) {
        this.sender = sender;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiver) {
        this.receiverName = receiver;
    }

    public ReceiverType getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(ReceiverType receiverType) {
        this.receiverType = receiverType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
