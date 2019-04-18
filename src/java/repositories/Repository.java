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
            if (user.getPassword().equals(password)) {
                session.getUserProperties().put("username", username);
                user.setSession(session);

                session.getAsyncRemote().sendObject(
                        new StatusMessage("status", "login", true, "Success"));

                // send the messages from the db to the client
                List<MessageBO> allMessages = em.createNamedQuery("Message.sender-receiver-ordered", MessageBO.class)
                        .setParameter("sender", user)
                        .setParameter("receiverName", user.getUsername())
                        .setParameter("receiverType", ReceiverType.USER)
                        .getResultList();

                allMessages.forEach(messageBO ->
                        System.out.println(
                                "Date:" + new Date(messageBO.getTime())
                                        + " "
                                        + messageBO.getSender().getUsername()
                                        + ": "
                                        + messageBO.getContent()));
                for (MessageBO message : allMessages) {
                    ChatMessage chatMsg = new ChatMessage(
                            "chat",
                            message.getSender().getUsername(),
                            message.getReceiverName(),
                            message.getReceiverType().name(),
                            message.getTime(),
                            message.getContent()
                    );
                    System.out.println(message.getContent());
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
            UserBO receiver = em.createNamedQuery("User.get-with-username", UserBO.class)
                    .setParameter("username", chatMsg.getReceiver())
                    .getSingleResult();

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
                Session memberSession = member.getSession();
                if (memberSession != null && memberSession.isOpen()) {
                    memberSession.getAsyncRemote().sendObject(chatMsg);
                }
            }
        }
    }

    public void manageGroup(Session session, GroupMessage groupMsg) {
        /*
        String action = groupMsg.getAction();

        UserBO user = em.createNamedQuery("User.get-with-username", UserBO.class)
                .setParameter("username", session.getUserProperties().get("username"))
                .getSingleResult();
        if (action.equals("create")) {
            long count = em.createNamedQuery("Chat.count-name", Long.class)
                    .setParameter("name", groupMsg.getName())
                    .getSingleResult();
            if (count == 0) {
                ChatBO group = new ChatBO(groupMsg.getName());
                em.getTransaction().begin();
                group.getUsers().add(user);
                em.persist(group);
                em.getTransaction().commit();
            } else {
                // TODO: Response group already exists
            }
        } else if (action.equals("join")) {
            List<ChatBO> groups = em.createNamedQuery("Chat.get-with-name", ChatBO.class)
                    .setParameter("name", groupMsg.getName())
                    .getResultList();

            if (groups.size() == 1) {
                ChatBO group = groups.get(0);
                em.getTransaction().begin();
                group.getUsers().add(user);
                em.getTransaction().commit();
            } else {
                // TODO: Response group doesn't exist
            }
        }
        */
    }

}
