package cn.iecas.springboot.framework.websocket;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
@Slf4j
public class WebSocketServer {

    // 线程安全 set
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    // 与某个客户端的连接会话
    private Session session;

    // 接受 sid
    private String sid = "";

    // 连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        log.info("有新的连接加入:" + sid);
        this.sid = sid;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "连接成功");
            sendObject(jsonObject);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    // 连接关闭调用的方法
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("有一连接关闭");
    }

    // 收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自连接:" + sid + "的消息:" + message);
    }

    // 连接错误调用的方法
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage());
    }

    // 服务器主动推送
    public void sendObject(Object object) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(object);
    }

    // 群发自定义信息
    public static void sendInfo(Object object, @PathParam("sid") String sid) throws IOException, EncodeException {
        for (WebSocketServer webSocketServer : webSocketSet) {
            if (sid == null) {
                webSocketServer.sendObject(object);
            } else if (webSocketServer.sid.equals(sid)) {
                webSocketServer.sendObject(object);
            }
        }
    }
}
