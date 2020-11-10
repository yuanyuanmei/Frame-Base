package com.ljcx.framework.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Slf4j
public class EchoServer {

    /**
     * NioEventLoop并不是一个纯粹的I/O线程，它除了负责I/O的读写之外
     * 创建了两个NioEventLoopGroup,
     * 他们实际是两个独立的Reactor线程池。
     * 一个用于接收客户端的TCP连接
     * 另一个用于处理I/O相关的读写操作，或者执行系统TasK、定时任务Task等
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;

    /**
     * 启动服务
     * @param hostname
     * @param port
     * @throws Exception
     */
    public ChannelFuture start(String hostname,int port){

        ChannelFuture f = null;
        try {
            //ServerBootstrap负责初始化netty服务器
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup) //绑定线程池
                    .channel(NioServerSocketChannel.class)  //指定使用的channel
                    .localAddress(new InetSocketAddress(hostname, port)) // 绑定监听端口
                    .childHandler(new ChannelInitializer<SocketChannel>() { //绑定客户端连接
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            //websocket协议本身是基于http协议的，所以这边也要使用http解码器
                            socketChannel.pipeline().addLast(new HttpServerCodec());
                        }
                    });

            f = b.bind().sync();
            channel = f.channel();
            log.info("丹妹温馨提示:===========EchoServer启动成功！！！=================");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //destroy();
            if(f != null && f.isSuccess()){
                log.info("丹妹温馨提示:Netty server listening " + hostname + " on port " + port + " and ready for connections...");
            }else{
                log.error("丹妹温馨提示:Netty server start up Error");
            }
        }

        return f;

    }

    /**
     * 停止服务
     */
    public void destroy(){
        log.info("丹妹温馨提示:Shutdown Netty Server....");
        if(channel != null){ channel.close(); }
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("丹妹温馨提示:Shutdown Netty Server Success....");
    }

}
