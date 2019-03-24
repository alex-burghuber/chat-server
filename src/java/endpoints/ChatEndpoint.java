package endpoints;

import decoders.MessageDecoder;
import encoders.MessageEncoder;
import messages.AuthMessage;
import messages.ChatMessage;
import messages.GroupMessage;
import messages.Message;
import repositories.Repository;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author Alexander Burghuber
 */
@ServerEndpoint(
        value = "/chat",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class ChatEndpoint {

    @OnOpen
    public void onOpen() {
        System.out.println("onOpen");
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        System.out.println("onMessage");

        if (message instanceof AuthMessage) {
            String action = ((AuthMessage) message).getAction();
            if (action.equals("register")) {
                Repository.getInstance().register(session, (AuthMessage) message);
            } else if (action.equals("login")) {
                Repository.getInstance().login(session, (AuthMessage) message);
            }
        } else if (session.getUserProperties().containsKey("username")) {
            if (message instanceof ChatMessage) {
                Repository.getInstance().sendChat(session, (ChatMessage) message);
            } else if (message instanceof GroupMessage) {
                Repository.getInstance().manageGroup(session, (GroupMessage) message);
            }
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