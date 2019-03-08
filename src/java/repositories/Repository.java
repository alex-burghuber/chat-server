package repositories;

import transferObjects.MessageVO;
import transferObjects.UserVO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.websocket.Session;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Burghuber
 */
public class Repository {

    private static Repository ourInstance = new Repository();
    private EntityManager em;
    private Set<UserVO> users = Collections.synchronizedSet(new HashSet<>());

    private Repository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChatPU");
        this.em = emf.createEntityManager();
    }

    public static Repository getInstance() {
        return ourInstance;
    }

    public void addUser(UserVO user) {
        users.add(user);
    }

    public void sendMessage(Session session, MessageVO message) {
        UserVO user = null;
        for (UserVO userOfSet : users) {
            if (userOfSet.getSession().equals(session)) {
                user = userOfSet;
            }
        }

        if (user != null) {
            String to = message.getTo();
            for (UserVO userOfSet : users) {
                if (userOfSet.getUsername().equals(to)) {
                    userOfSet.getSession().getAsyncRemote().sendText(user.getUsername() + ": " + message.getContent());
                }
            }

        } else {
            System.out.println("UserVO not found.");
        }
    }

}
