package com.ljcx.code;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(9090).start();
    }

    private void start() throws Exception{
        final EchoServerHandler serverHandler = new EchoServerHandler();

        /**
         * NioEventLoop并不是一个纯粹的I/O线程，它除了负责I/O的读写之外
         * 创建了两个NioEventLoopGroup,
         * 他们实际是两个独立的Reactor线程池。
         * 一个用于接收客户端的TCP连接
         * 另一个用于处理I/O相关的读写操作，或者执行系统TasK、定时任务Task等
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        //ServerBootstrap负责初始化netty服务器
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //为监听客户端read/write事件的Channel添加用户自定义的ChannelHandler
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                });

        ChannelFuture f = b.bind().sync();
        f.channel().closeFuture().sync();


    }

}
