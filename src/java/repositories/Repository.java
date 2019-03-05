package repositories;

import entities.Message;
import entities.User;

import javax.websocket.Session;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Burghuber
 */
public class Repository {

    private static Repository ourInstance = new Repository();
    //private EntityManager em;
    private Set<User> users = Collections.synchronizedSet(new HashSet<User>());

    private Repository() {
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChatPU");
        //this.em = emf.createEntityManager();
    }

    public static Repository getInstance() {
        return ourInstance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void sendMessage(Session session, Message message) {

        User user = null;
        for (User userOfSet : users) {
            if (userOfSet.getSession().equals(session)) {
                user = userOfSet;
            }
        }

        if (user != null) {
            String to = message.getTo();
            for (User userOfSet : users) {
                if (userOfSet.getUsername().equals(to)) {
                    userOfSet.getSession().getAsyncRemote().sendText(message.getContent());
                }
            }

        } else {
            System.out.println("User not found.");
        }
    }

}


