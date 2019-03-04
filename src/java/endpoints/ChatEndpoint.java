package endpoints;

import entities.User;
import repositories.Repository;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Alexander Burghuber
 */
@ServerEndpoint("/chat/{username}")
public class ChatEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        Repository.getInstance().addUser(new User(session, username));
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        Repository.getInstance().sendMessage(session, message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
    }

}