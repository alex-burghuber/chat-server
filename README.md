# chat-server
Simple Java Websocket Chat-Server

## API

### Requests:

#### Chat
```json
{
  "type": "chat",
  "target": "user/group",
  "name": "<name of user or group>",
  "content": "<content>"
}
```
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