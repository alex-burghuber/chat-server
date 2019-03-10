package decoders;

import messages.ChatMessage;
import messages.GroupMessage;
import messages.Message;
import org.json.JSONObject;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String str) throws DecodeException {
        JSONObject json = new JSONObject(str);
        if (json.has("chat")) {
            JSONObject chatJson = json.getJSONObject("chat");
            return new ChatMessage("chat",
                    chatJson.getString("target"),
                    chatJson.getString("name"),
                    chatJson.getString("content"));
        } else if (json.has("group")) {
            JSONObject groupJson = json.getJSONObject("group");
            return new GroupMessage("group",
                    groupJson.getString("action"),
                    groupJson.getString("name"));
        } else {
            throw new DecodeException(str, "Can't decode");
        }
    }

    @Override
    public boolean willDecode(String str) {
        // TODO: check if valid JSON
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
