package repositories;

import entities.User;
import org.json.JSONException;
import org.json.JSONObject;

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

    public void sendMessage(Session session, String message) {
        try {
            JSONObject json = new JSONObject(message);

            User user = null;
            for (User userOfSet : users) {
                if (userOfSet.getSession().equals(session)) {
                    user = userOfSet;
                }
            }

            if (user != null) {
                String messageToSend = json.optString("message");
                if (!messageToSend.isEmpty()) {
                    for (User follower : user.getFollowers()) {
                        follower.getSession().getAsyncRemote().sendText(messageToSend);
                    }
                }

                String register = json.optString("register");
                if (!register.isEmpty()) {
                    for (User userOfSet : users) {
                        if (userOfSet.getUsername().equals(register)) {
                            userOfSet.getFollowers().add(user);
                        }
                    }
                }

                String unregister = json.optString("unregister");
                if (!unregister.isEmpty()) {
                    for (User userOfSet : users) {
                        if (userOfSet.getUsername().equals(unregister)) {
                            userOfSet.getFollowers().remove(user);
                        }
                    }
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

}
