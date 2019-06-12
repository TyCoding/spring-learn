# Spring Boot 整合 Websocket

## 概念引入

`Websocket` 是一种网络通信协议。

和HTTP协议不同，HTTP协议通信只能由客户端发起。比如我们常见的CRUD都仅仅是服务端请求客户端的接口，最终消息由客户端推送给服务端，服务端再实现数据回显等。

`Websocket` 最大的特点就是实现了服务器可以主动向客户端推送消息，客户端也可以主动向服务器发送消息，实现了真正的双向平等会话。

如果在服务端实现`Websocket`主动向客户端推送消息，需要使用JavaScript的`WebSocket`对象，它是JavaScript内置的对象，比如我们创建一个`Websocket`连接对象：

```javascript
var websocket = new WebSocket('ws://...')
```

注意和HTTP通信不同在于，协议的标识符不再是`http`或`https`，而是`ws`或`wss`

也就是说，服务服务端想主动向客户端推送消息，需要使用JavaScript内置的`WebSocket`对象实现请求。那么对应客户端需要一个WebSocket提供一个接口用于接收`websocket`请求，这个请求接口并不等同于HTTP的Rest接口，请注意区分

## 环境准备

1. 引入依赖

```xml
<!-- Spring Websocket -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>

<!-- Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!--Lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>

<!-- Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

2. 配置WebSocket需要自动注入的Servlet

```java
@Configuration
public class WebsocketAutoConfig {

    @Bean
    public ServerEndpointExporter endpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

## 代码实现

前端使用Websocket主动推送消息：

```javascript
var websocket = null;

/**
 * 链接WebSocket
 */
function connectChat() {
    console.log(">> 链接WebSocket")
    websocket = new WebSocket('ws://localhost:8080/chat/' + (new Date()).getTime())

    /**
     * Websocket链接错误时调用
     */
    websocket.onerror = function() {
        writeHTML('Websocket链接发生错误')
    }

    /**
     * Websocket链接成功时调用
     */
    websocket.onopen = function() {
        writeHTML('Websocket链接成功')
    }

    /**
     * 接收到消息的回调方法
     */
    websocket.onmessage = function(event) {
        console.log(event)
        writeHTML(event.data)
    }

    /**
     * 链接关闭时的回调方法
     */
    websocket.onclose = function() {
        writeHTML('Websocket链接关闭')
    }
}

/**
 * 关闭WebSock链接
 */
function closeChat() {
    websocket.close()
}
```

服务端接收推送消息

```java
@Slf4j
@Component
@ServerEndpoint("/chat/{id}")
public class WebsocketServerEndpoint {

    //在线连接数
    private static int onlineCount = 0;

    //用于存放当前Websocket对象的Set集合
    private static CopyOnWriteArraySet<WebsocketServerEndpoint> websocketServerEndpoints = new CopyOnWriteArraySet<>();

    //与客户端的会话Session
    private Session session;
    
    //会话窗口的ID标识
    private String id = "";

    /**
     * 链接成功调用的方法
     *
     * @param session
     * @param id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        log.info("onOpen >> 链接成功");
        this.session = session;

        //将当前websocket对象存入到Set集合中
        websocketServerEndpoints.add(this);

        //在线人数+1
        addOnlineCount();

        log.info("有新窗口开始监听：" + id + ", 当前在线人数为：" + getOnlineCount());

        this.id = id;

        try {
            sendMessage("有新窗口开始监听：" + id + ", 当前在线人数为：" + getOnlineCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("onClose >> 链接关闭");

        //移除当前Websocket对象
        websocketServerEndpoints.remove(this);

        //在内线人数-1
        subOnLineCount();

        log.info("链接关闭，当前在线人数：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("接收到窗口：" + id + " 的信息：" + message);

        //发送信息
        for (WebsocketServerEndpoint websocketServerEndpoint : websocketServerEndpoints) {
            try {
                websocketServerEndpoint.sendMessage("接收到窗口：" + id + " 的信息：" + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable e) {
        e.printStackTrace();
    }

    /**
     * 推送消息
     *
     * @param message
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 自定义推送消息
     *
     * @param message
     * @param id
     */
    public static void sendInfo(String id, String message) {
        log.info("推送消息到窗口：" + id + " ，推送内容：" + message);
        for (WebsocketServerEndpoint endpoint : websocketServerEndpoints) {
            try {
                if (id == null) {
                    endpoint.sendMessage(message);
                } else if (endpoint.id.equals(id)) {
                    endpoint.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private void subOnLineCount() {
        WebsocketServerEndpoint.onlineCount--;
    }
    
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private void addOnlineCount() {
        WebsocketServerEndpoint.onlineCount++;
    }
}
```

## 截图

![](doc/20190612113448.png)

![](doc/13AF3253225850776AE4276753778333.jpg)

