package endpoints;

import decoders.MessageDecoder;
import encoders.MessageEncoder;
import messages.GroupMessage;
import messages.Message;
import repositories.Repository;
import messages.ChatMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author Alexander Burghuber
 */
@ServerEndpoint(
        value = "/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class ChatEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        System.out.println("onOpen");
        Repository.getInstance().addUser(session, username);
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        System.out.println("onMessage");
        if (message instanceof ChatMessage) {
            Repository.getInstance().sendChat(session, (ChatMessage) message);
        } else if (message instanceof GroupMessage) {
            Repository.getInstance().manageGroup(session, (GroupMessage) message);
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}