package endpoints;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Alexander Burghuber
 */
@ServerEndpoint("/chat")
public class ChatEndpoint {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) throws IOException {
        sessions.add(session);
    }

    @OnMessage
    public void distribute(String message, Session session) {
        for (Session client : sessions) {
            client.getAsyncRemote().sendText(message);
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

}