package repositories;

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
    private Set<User> users = Collections.synchronizedSet(new HashSet<User>());

    private Repository() {
    }

    public static Repository getInstance() {
        return ourInstance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void sendMessage(String message, Session session) {
        for (User user : users) {
            if (user.getSession().equals(session)) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }
}
