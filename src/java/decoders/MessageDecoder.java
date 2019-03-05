package decoders;

import entities.Message;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String str) {
        JSONObject json = new JSONObject(str);
        return new Message(json.getString("to"), json.getString("content"));
    }

    @Override
    public boolean willDecode(String str) {
        try {
            JSONObject json = new JSONObject(str);
            String to = json.optString("to");
            String content = json.optString("content");
            if (!to.isEmpty() && !content.isEmpty()) {
                return true;
            }
        } catch (JSONException je) {
            return false;
        }
        return false;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
