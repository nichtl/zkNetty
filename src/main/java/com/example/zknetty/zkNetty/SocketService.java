package com.example.zknetty.zkNetty;

/**
 * Socket 接口
 * Created by Jason Zhang on 2017/06/29.
 */
public interface SocketService {

    /***
     * 设置监听
     * @param listener
     */
    void setOnSocketListener(OnSocketListener listener);

    /**
     * 启动
     */
    void start(int port);

    /***
     * 关闭
     */
    void shutDown();

    /***
     * 重制
     */
    void reset();

    /***
     * 发送消息
     * @param channel   通道
     * @param message   消息
     */
    void sendMessage(Object channel, Object message);

    /**
     * 判断当前是否在线
     * @param channel
     * @return
     */
    boolean isOnline(Object channel);
    
}
