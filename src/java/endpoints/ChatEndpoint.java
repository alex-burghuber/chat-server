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
    public void onMessage(String message, Session session) {
        Repository.getInstance().sendMessage(message, session);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
    }

}