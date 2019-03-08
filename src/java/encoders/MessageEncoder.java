package encoders;

import org.json.JSONObject;
import transferObjects.MessageVO;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<MessageVO> {

    @Override
    public String encode(MessageVO message) {
        return new JSONObject(message).toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
