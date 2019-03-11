package encoders;

import messages.Message;
import org.json.JSONObject;
import messages.ChatMessage;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) {
        return new JSONObject(message).toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
