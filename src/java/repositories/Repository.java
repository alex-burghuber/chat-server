package repositories;

import entities.GroupBO;
import entities.MessageBO;
import entities.UserBO;
import enums.ReceiverType;
import messages.AuthMessage;
import messages.ChatMessage;
import messages.GroupMessage;
import messages.StatusMessage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.websocket.Session;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Burghuber
 */
public class Repository {

    private static Repository ourInstance = new Repository();
    private EntityManager em;

    private Repository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChatPU");
        em = emf.createEntityManager();
    }

    public static Repository getInstance() {
        return ourInstance;
    }

    public void register(Session session, AuthMessage authMsg) {
        String username = authMsg.getUsername();
        String password = authMsg.getPassword();

        long count = em.createNamedQuery("User.count-username", Long.class)
                .setParameter("username", username)
                .getSingleResult();

        StatusMessage status;
        if (count == 0) {
            em.getTransaction().begin();
            em.persist(new UserBO(session, username, password));
            em.getTransaction().commit();
            status = new StatusMessage("status", "register", true, "Success");
        } else {
            status = new StatusMessage("status", "register", false, "Username is already taken");
        }
        session.getAsyncRemote().sendObject(status);
    }

    public void login(Session session, AuthMessage authMsg) {
        String username = authMsg.getUsername();
        String password = authMsg.getPassword();

        List<UserBO> users = em.createNamedQuery("User.get-with-username", UserBO.class)
                .setParameter("username", username)
                .getResultList();

        if (users.size() == 1) {
            UserBO user = users.get(0);
            em.refresh(user);
            if (user.getPassword().equals(password)) {
                session.getUserProperties().put("username", username);
                user.setSession(session);

                session.getAsyncRemote().sendObject(
                        new StatusMessage("status", "login", true, "Success"));

                // send the messages from the db to the client
                List<MessageBO> allMessages = em.createNamedQuery("Message.sender-receiver", MessageBO.class)
                        .setParameter("sender", user)
                        .setParameter("receiverName", user.getUsername())
                        .setParameter("receiverType", ReceiverType.USER)
                        .getResultList();

                List<MessageBO> groupMessages = null;
                for (GroupBO group : user.getGroups()) {
                    System.out.println(user.getUsername() + "-Groups: " + group.getName());
                    List<MessageBO> currGroupMessages = em.createNamedQuery("Message.get-with-name", MessageBO.class)
                            .setParameter("receiverName", group.getName())
                            .setParameter("receiverType", ReceiverType.GROUP)
                            .getResultList();
                    if (groupMessages == null) {
                        groupMessages = currGroupMessages;
                    } else {
                        groupMessages.addAll(currGroupMessages);
                    }
                }

                if (groupMessages != null) {
                    allMessages.addAll(groupMessages);
                }

                for (MessageBO message : allMessages) {
                    System.out.println("Sender: " + message.getSender().getUsername()
                            + " Receiver: " + message.getReceiverName()
                            + " Content: " + message.getContent());
                    ChatMessage chatMsg = new ChatMessage(
                            "chat",
                            message.getSender().getUsername(),
                            message.getReceiverName(),
                            message.getReceiverType().name().toLowerCase(),
                            message.getTime(),
                            message.getContent()
                    );
                    session.getAsyncRemote().sendObject(chatMsg);
                }
            } else {
                session.getAsyncRemote().sendObject(
                        new StatusMessage("status", "login", false, "Username or password is wrong"));
            }
        } else {
            session.getAsyncRemote().sendObject(
                    new StatusMessage("status", "login", false, "Username or password is wrong"));
        }

    }

    public void send(Session session, ChatMessage chatMsg) {
        UserBO sender = em.createNamedQuery("User.get-with-username", UserBO.class)
                .setParameter("username", chatMsg.getSender())
                .getSingleResult();
        String kind = chatMsg.getKind();
        if (kind.equals("user")) {
            List<UserBO> receivers = em.createNamedQuery("User.get-with-username", UserBO.class)
                    .setParameter("username", chatMsg.getReceiver())
                    .getResultList();
            if (receivers.size() != 0) {
                UserBO receiver = receivers.get(0);
                // save msg on the database
                MessageBO messageBO = new MessageBO(
                        sender,
                        receiver.getUsername(),
                        ReceiverType.USER,
                        chatMsg.getTime(),
                        chatMsg.getContent()
                );
                System.out.println(new Date());
                System.out.println(messageBO.getTime());
                em.getTransaction().begin();
                em.persist(messageBO);
                em.getTransaction().commit();

                // send to msg to receiver
                Session toSession = receiver.getSession();
                if (toSession != null && toSession.isOpen()) {
                    System.out.println("Send ChatMessage: " + chatMsg.getContent());
                    toSession.getAsyncRemote().sendObject(chatMsg);
                } else {
                    System.out.println("To-User is not online");
                }
            } else {
                StatusMessage status = new StatusMessage("status", "chat",
                        false, "Error sending message: User does not exist");
                session.getAsyncRemote().sendObject(status);
            }
        } else if (kind.equals("group")) {
            GroupBO group = em.createNamedQuery("Group.get-with-name", GroupBO.class)
                    .setParameter("name", chatMsg.getReceiver())
                    .getSingleResult();

            // save msg on the database
            MessageBO messageBO = new MessageBO(
                    sender,
                    group.getName(),
                    ReceiverType.GROUP,
                    chatMsg.getTime(),
                    chatMsg.getContent()
            );
            em.getTransaction().begin();
            em.persist(messageBO);
            em.getTransaction().commit();

            // send message to group users
            for (UserBO member : group.getUsers()) {
                if (!member.getUsername().equals(sender.getUsername())) {
                    Session memberSession = member.getSession();
                    if (memberSession != null && memberSession.isOpen()) {
                        memberSession.getAsyncRemote().sendObject(chatMsg);
                    }
                }
            }
        }
    }

    public void manageGroup(Session session, GroupMessage groupMsg) {
        UserBO user = em.createNamedQuery("User.get-with-username", UserBO.class)
                .setParameter("username", session.getUserProperties().get("username"))
                .getSingleResult();

        List<GroupBO> groups = em.createNamedQuery("Group.get-with-name", GroupBO.class)
                .setParameter("name", groupMsg.getName())
                .getResultList();

        if (groups.size() == 0) {
            GroupBO group = new GroupBO(groupMsg.getName());
            group.getUsers().add(user);
            em.getTransaction().begin();
            em.persist(group);
            em.getTransaction().commit();
        } else if (groups.size() == 1) {
            GroupBO group = groups.get(0);
            group.getUsers().add(user);
        }

    }

}
