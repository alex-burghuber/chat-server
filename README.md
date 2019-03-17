# chat-server
Simple Java Websocket Chat-Server

## API
### Messages
```json
{
  "chat": {
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

### Authentication
```json
{
  "auth": {
      "action": "register/login",
      "username": "<username of user>",
      "password": "<password of user>"
  }
}
```
