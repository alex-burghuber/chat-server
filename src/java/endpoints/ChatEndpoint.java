package endpoints;

import decoders.MessageDecoder;
import encoders.MessageEncoder;
import repositories.Repository;
import transferObjects.MessageVO;
import transferObjects.UserVO;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Alexander Burghuber
 */
@ServerEndpoint(value = "/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class ChatEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        Repository.getInstance().addUser(new UserVO(session, username));
    }

    @OnMessage
    public void onMessage(Session session, MessageVO message) {
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