package com.ljcx.framework.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.activemq.ActivemqProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * websocket
 */

@Slf4j
@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class ApplicationWebSocket {

    @Autowired
    private ActivemqProducer producer;

    //静态变量，用来记录当前在线连接数。设计成线程安全的
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    //concurrent包的线程安全set，用来存放每个客户端对应的ApplicationWebSocket对象
    private static CopyOnWriteArraySet<ApplicationWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String userId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session){
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount(); //在线人数+1
        this.userId = userId;
        log.info("丹妹温馨提示：有新连接加入！当前在线人数为"+getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this); //从set中删除
        subOnlineCount(); //在线数减1
        log.info("丹妹温馨提示：有一连接关闭！当前在线人数为"+getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("丹妹温馨提示：来自于客户端的消息"+message);
        //群发消息
        JSONObject jsonTo = JSON.parseObject(message);
        String userId = jsonTo.containsKey("userId") ? jsonTo.getString("userId") : "";
        String heartBeat = jsonTo.containsKey("heartBeat") ? jsonTo.getString("heartBeat") : "";
        if (!StringUtils.isBlank(userId) && !StringUtils.isBlank(heartBeat)) {
            this.sendMessage(message, userId);
        }
    }

    /**
     * 发送错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.info("丹妹温馨提示：发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message,String toUser) throws IOException {
        //getBasicRemote是阻塞式的
        //this.session.getBasicRemote().sendText(message);
        //非阻塞式的
        for(ApplicationWebSocket item: webSocketSet){
            if(item.userId.equals(toUser)){
                item.session.getAsyncRemote().sendText(message);
                break;
            }
        }

    }

    /**
     * 群发自定义消息
     */
    public static void sendMessageAll(String message){
        for(ApplicationWebSocket item : webSocketSet){
            try {
                item.sendMessage(message,item.userId);
            } catch (IOException e) {
                continue;
            }
        }
    }


    /**
     * 获取在线人数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 在线人数加1
     */
    private void addOnlineCount() {
        ApplicationWebSocket.onlineCount.getAndIncrement();
    }

    /**
     * 在线人数减1
     */
    public static synchronized void subOnlineCount() {
        ApplicationWebSocket.onlineCount.getAndDecrement();
    }

}
