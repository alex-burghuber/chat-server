package repositories;

import entities.GroupBO;
import entities.UserBO;
import messages.ChatMessage;
import messages.GroupMessage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

/**
 * @author Alexander Burghuber
 */
public class Repository {

    private static Repository ourInstance = new Repository();
    private EntityManager em;
    //private Set<UserVO> users = Collections.synchronizedSet(new HashSet<>());

    private Repository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChatPU");
        em = emf.createEntityManager();
    }

    public static Repository getInstance() {
        return ourInstance;
    }

    public void addUser(Session session, String username) {
        List<UserBO> users = em.createNamedQuery("User.get-with-username", UserBO.class)
                .setParameter("username", username)
                .getResultList();

        if (users.size() == 0) {
            session.getUserProperties().put("username", username);
            em.getTransaction().begin();
            em.persist(new UserBO(session, username));
            em.getTransaction().commit();
        } else if (users.size() == 1) {
            session.getUserProperties().put("username", username);
            users.get(0).setSession(session);
        } else {
            // TODO: Response username is already taken
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendChat(Session session, ChatMessage chatMessage) {
        String target = chatMessage.getTarget();
        if (target.equals("user")) {
            List<UserBO> users = em.createNamedQuery("User.get-with-username", UserBO.class)
                    .setParameter("username", chatMessage.getName())
                    .getResultList();

            if (users.size() == 1) {
                UserBO userOfTarget = users.get(0);
                Session sessionOfTarget = userOfTarget.getSession();
                if (sessionOfTarget != null && sessionOfTarget.isOpen()) {
                    String usernameOfSender = (String) session.getUserProperties().get("username");
                    sessionOfTarget.getAsyncRemote().sendText("From " + usernameOfSender + ": " + chatMessage.getContent());
                } else {
                    // TODO: Response target is not online
                }
            } else {
                // TODO: Response user does not exist
            }
        } else if (target.equals("group")) {
            UserBO user = em.createNamedQuery("User.get-with-username", UserBO.class)
                    .setParameter("username", session.getUserProperties().get("username"))
                    .getSingleResult();

            // Refresh the user because of the many-to-many relation
            em.refresh(user);

            List<GroupBO> groupsOfUser = user.getGroups();
            for (GroupBO group : groupsOfUser) {
                if (group.getName().equals(chatMessage.getName())) {
                    List<UserBO> members = group.getMembers();
                    for (UserBO member : members) {
                        Session memberSession = member.getSession();
                        if (memberSession != null && memberSession.isOpen()) {
                            member.getSession().getAsyncRemote().sendText(chatMessage.getContent());
                        }
                    }
                }
            }
        }

    }

    public void manageGroup(Session session, GroupMessage message) {
        String action = message.getAction();

        UserBO user = em.createNamedQuery("User.get-with-username", UserBO.class)
                .setParameter("username", session.getUserProperties().get("username"))
                .getSingleResult();
        if (action.equals("create")) {
            long count = em.createNamedQuery("Group.count-name", Long.class)
                    .setParameter("name", message.getName())
                    .getSingleResult();
            if (count == 0) {
                GroupBO group = new GroupBO(message.getName());
                em.getTransaction().begin();
                group.getMembers().add(user);
                em.persist(group);
                em.getTransaction().commit();
            } else {
                // TODO: Repsonse group already exists
            }
        } else if (action.equals("join")) {
            List<GroupBO> groups = em.createNamedQuery("Group.get-with-name", GroupBO.class)
                    .setParameter("name", message.getName())
                    .getResultList();

            if (groups.size() == 1) {
                GroupBO group = groups.get(0);
                em.getTransaction().begin();
                group.getMembers().add(user);
                em.getTransaction().commit();
            } else {
                // TODO: Response group doesn't exist
            }
        }
    }
}
