package decoders;

import messages.AuthMessage;
import messages.ChatMessage;
import messages.GroupMessage;
import messages.Message;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String str) throws DecodeException {
        JSONObject json = new JSONObject(str);
        String type = json.getString("type");
        Message message;
        switch (type) {
            case "chat":
                message = new ChatMessage("chat",
                        json.getString("sender"),
                        json.getString("receiver"),
                        json.getString("kind"),
                        json.getLong("time"),
                        json.getString("content"));
                break;
            case "group":
                message = new GroupMessage("group",
                        json.getString("action"),
                        json.getString("name"));
                break;
            case "auth":
                message = new AuthMessage("auth",
                        json.getString("action"),
                        json.getString("username"),
                        json.getString("password"));
                break;
            default:
                throw new DecodeException(str, "Can't decode");
        }
        System.out.println("Decoded: " + str);
        return message;
    }

    @Override
    public boolean willDecode(String str) {
        try {
            JSONObject json = new JSONObject(str);
            String type = json.optString("type");
            if (type != null) {
                switch (type) {
                    case "chat":
                        if (json.optString("sender") != null
                                && json.optString("receiver") != null
                                && json.optString("kind") != null
                                && json.optLong("time") != 0L
                                && json.optString("content") != null) {
                            return true;
                        }
                        break;
                    case "group":
                        if (json.optString("action") != null
                                && json.optString("name") != null) {
                            return true;
                        }
                        break;
                    case "auth":
                        if (json.optString("action") != null
                                && json.optString("username") != null
                                && json.optString("password") != null) {
                            return true;
                        }
                        break;
                }
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        System.out.println("Will not decode: " + str);
        return false;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
