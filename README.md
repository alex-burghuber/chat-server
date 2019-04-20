# Chat Server
Java Websocket Server for [Chat Client](https://github.com/Alexander-Burghuber/chat-client)

## API

#### Chat
```json
{
  "type": "chat",
  "sender": "<name of user>",
  "receiver": "<name of user/group>",
  "kind": "user/group",
  "time": "<sent date in milliseconds>",
  "content": "<content>"
}
```

### Requests:

#### Group managing
```json
{
  "type": "group",
  "name": "<name of group>"
}
```

#### Authentication
```json
{
  "type": "auth",
  "action": "register/login",
  "username": "<username of user>",
  "password": "<password of user>"
}
```

### Responses:

#### Status
```json
{
  "type": "status",
  "kind": "register/login",
  "success": "true/false",
  "content": "<content>"
}
```
