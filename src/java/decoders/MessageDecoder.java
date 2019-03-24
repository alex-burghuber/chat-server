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
        Message message;
        if (json.has("chat")) {
            JSONObject chatJson = json.getJSONObject("chat");
            message = new ChatMessage("chat",
                    chatJson.getString("target"),
                    chatJson.getString("name"),
                    chatJson.getString("content"));
        } else if (json.has("group")) {
            JSONObject groupJson = json.getJSONObject("group");
            message = new GroupMessage("group",
                    groupJson.getString("action"),
                    groupJson.getString("name"));
        } else if (json.has("auth")) {
            JSONObject authJson = json.getJSONObject("auth");
            message = new AuthMessage("auth",
                    authJson.getString("action"),
                    authJson.getString("username"),
                    authJson.getString("password"));
        } else {
            throw new DecodeException(str, "Can't decode");
        }
        System.out.println("Decoded: " + str);
        return message;
    }

    @Override
    public boolean willDecode(String str) {
        try {
            JSONObject outerJson = new JSONObject(str);
            JSONObject innerJson = outerJson.optJSONObject("chat");
            if (innerJson != null) {
                if (innerJson.optString("target") != null
                        && innerJson.optString("name") != null
                        && innerJson.optString("content") != null) {
                    return true;
                }
            }
            innerJson = outerJson.optJSONObject("group");
            if (innerJson != null) {
                if (innerJson.optString("action") != null
                        && innerJson.optString("name") != null) {
                    return true;
                }
            }
            innerJson = outerJson.optJSONObject("auth");
            if (innerJson != null) {
                if (innerJson.optString("action") != null
                        && innerJson.optString("username") != null
                        && innerJson.optString("password") != null) {
                    return true;
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
