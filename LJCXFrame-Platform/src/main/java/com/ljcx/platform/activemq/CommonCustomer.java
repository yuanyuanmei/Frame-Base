package com.ljcx.platform.activemq;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.utils.DateUtils;
import com.ljcx.common.utils.ReflectUtil;
import com.ljcx.framework.netty.ApplicationWebSocket;
import com.ljcx.user.service.UserBaseService;
import com.ljcx.user.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class CommonCustomer {

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private ApplicationWebSocket webSocket;

    /**
     * 发送团队消息
     */
    public void sendTeamMessage(Object info,String type,Long teamId) throws IOException {
        List<MemberVo> userList = userBaseService.listUpTeamByTeamId(teamId);
        for(MemberVo user:userList){
            JSONObject msg = new JSONObject();
            msg.put("messageType",type);
            msg.put("data",info);
            log.info("团队消息发送成功,{}，发送时间为，{}",info, DateUtils.dateTimeNow("yyyy-MM-dd hh:mm:ss"));
            webSocket.sendMessage(msg.toString(),user.getId()+"");
        }
    }

    /**
     * 发送个人消息
     */
    public void sendSbMessage(Object info,String type,Long userId) throws IOException {
        JSONObject msg = new JSONObject();
        msg.put("messageType",type);
        msg.put("data",info);
        log.info("个人消息发送成功,{}，发送时间为，{}",info, DateUtils.dateTimeNow("yyyy-MM-dd hh:mm:ss"));
        webSocket.sendMessage(msg.toString(),userId+"");
    }


    /**
     * 获取消息对象
     * @param message
     * @return
     */
    public Object getInfo(Message message) {

        Object info = null;
        try {
            // 文本消息类型
            if(message instanceof TextMessage){
                TextMessage txt = (TextMessage) message;
                info = txt.getText();

            }

            // 二进制消息类型
            if (message instanceof ActiveMQBytesMessage){
                ActiveMQBytesMessage bytesMessage= (ActiveMQBytesMessage) message;
                Object[] params = null;
                ReflectUtil.invoke(bytesMessage, "initializeReading", params);
                DataInputStream dataInputStream = (DataInputStream) ReflectUtil.getFieldValue(bytesMessage, "dataIn");
                byte[] data = ReflectUtil.input2byte(dataInputStream);
                info = new String(data);
            }

            // steam消息类型
            if(message instanceof StreamMessage){
                StreamMessage streamMessage = (StreamMessage) message;
                info = streamMessage.readString();
            }

            // object消息
            if(message instanceof ObjectMessage){
                ObjectMessage om = (ObjectMessage) message;
                info = om.getObject().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }
}
