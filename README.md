# chat-server
Simple Java Websocket Chat-Server

## API

#### Chat
```json
{
  "type": "chat",
  "sender": "<name of user>",
  "receiver": "<name of user/group>",
  "kind": "user/group",
  "content": "<content>"
}
```

### Requests:

#### Group managing
```json
{
  "type": "group",
  "action": "create/join",
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