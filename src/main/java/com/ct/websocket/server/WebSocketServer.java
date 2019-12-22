package com.ct.websocket.server;

import com.ct.websocket.common.thread.PushScheduleWork;
import com.ct.websocket.entity.dto.DeviceListDTO;
import com.ct.websocket.server.impl.DeviceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;


/**
 * @auther:chent69
 * @date: 2019/12/16-16 :23
 */
@ServerEndpoint("/websocket/{sid}")
@Component
@Slf4j
public class WebSocketServer {
    @Autowired
    private DeviceServiceImpl deviceService;
    private static WebSocketServer webSocketServer;

    private PushScheduleWork pushScheduleWork;

    //通过@PostConstruct实现初始化bean之前进行的操作
    @PostConstruct
    public void init(){
        webSocketServer = this;
//        // 初使化时将已静态化的testService实例化
//        webSocketServer.deviceService = this.deviceService;
    }
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全HashMap，用来存放每个客户端对应的WebSocket对象。
    public static ConcurrentHashMap<String , WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid="";
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid=sid;
        webSocketMap.put(sid , this);     //加入Map中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:{},当前在线人数为{}",sid ,getOnlineCount());
        try {
            sendMessage("连接成功");
            pushScheduleWork = new PushScheduleWork(webSocketServer.deviceService , sid);
            pushScheduleWork.start();
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this.sid);  //从Map中删除
        pushScheduleWork.stop();
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为{}" , getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message) {
        log.info("收到来自窗口{}的信息:{}",sid ,message);
        //群发消息
        for (WebSocketServer item : webSocketMap.values()) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param error
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        log.info("推送消息到窗口"+sid+"，推送内容:"+message);
        if(sid.equals("all")){
            for (WebSocketServer item : webSocketMap.values()) {
                try {
                    //这里可以设定只推送给这个sid的，为null则全部推送
                    item.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            WebSocketServer item = webSocketMap.get(sid);
            if(item != null){
                item.sendMessage(message);
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
