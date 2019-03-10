# chat-server
Simple Java Websocket Server

## API
### Messages
```json
{
  "message": {
    "target": "user/group",
    "name": "<name of user or group>",
    "content": "<content>"
  }
}
```
### Group managing
```json
{
  "group": {
    "action": "create/join",
    "name": "<name of group>"
  }
}
```
