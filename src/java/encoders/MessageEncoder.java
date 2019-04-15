package encoders;

import messages.Message;
import org.json.JSONObject;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) {
        String jsonString = new JSONObject(message).toString();
        System.out.println("Encoded: " + jsonString);
        return jsonString;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
