package com.ljcx.framework.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Netty中部分核心类说明
 * Bootstrap 、 ServerBootstrap
 * Bootstrap 是引导，一个netty应用通常由一个Bootstrap开始，主要作用是配置
 * 整个netty程序，串联各个组件，Netty中Bootstrap类是客户端程序的启动引导类
 * ServerBootstrap是服务器端启动引导类
 *
 * Future
 * Future提供了另外一种在操作完成是通知应用程序的方式。这个对象可以看作一个异步操作
 * 的结果占位符。通俗地讲，它相当于一位指挥官，发送了一个请求建立完连接，通信完毕了
 * 你通知一声它回来关闭各项IO通道，整个过程，它是不阻塞的，异步的。
 * 在Netty中所有的IO操作都是异步的，不能立刻得知消息是否被正确处理，但是
 * 可以过一会等他执行完成或者直接注册一个监听，具体的实现就是通过future和channelfutures
 * 他们可以注册一个监听，当操作执行成功或失败时监听会自动触发注册的监听事件
 *
 * channel
 * channel 类似socket，它代表一个实体的开放连接，如读写操作，通俗的讲，channel字面意思就是
 * 通道，每一个客户端与服务器之间进行通讯的一个双向通道
 * channel主要工作：
 * 1.当前网络连接的通道的状态（是否打开，是否连接）
 * 2.网络连接的配置参数（例如接收缓冲区的大小）
 * 3.提供异步的网络I/O操作（如建立连接，读写，绑定端口），异步调用意味着任何I/O调用都将
 * 立即返回，并且不保证在调用结束时所请求的I/O操作已完成。调用立即返回一个ChannelFuture实例，
 * 通过注册监听器到channelFuture上，可以I/O操作成功，失败或取消时回调通知调用方
 * 4.支持关联I/O操作与对应的处理程序
 *
 * 不同协议，不同的阻塞类型的连接都有不同的channel类型与之对应，下面是一些常用的channel类型
 * NioSocketChannel 异步的客户端TCP Socket连接
 * NioServerSocketChannel 异步的服务器端TCP Socket连接
 * NioDatagramChannel 异步的UDP连接
 * NioSctpChannel 异步的客户端sctp连接
 * NioSctpServerChannel 异步的Sctp服务器端连接
 * 这些通道涵盖了UDP和TCP网络IO以及文件IO
 *
 * EventLoop接口
 * NioEventLoop中维护了一个线程和任务队列，支持异步提交执行任务，线程启动时会调用
 * NioEventLoop中的run方法，执行I/O任务和非I/O任务；
 * I/O任务
 * 即selectionKey中ready的事件，如accept,connect,read,write等，由processSelectedKeys方法触发
 * 非IO任务
 * 添加到taskQueue中的任务，如register(),bind()等任务，由runAllTasks方法触发
 * 两种任务的执行时间比由变量ioRatio控制，默认为50，则允许非IO任务执行的时间与IO任务的执行时间相等
 *
 * ChannelHandler
 * channelHandler是一个接口，处理I/O事件或拦截I/O操作，并将其转发到其channelPipeline（业务处理链）
 * 中的下一个处理程序
 * channelHandler本身并没有提供很多方法，因为这个接口有许多的方法需要实现，方便使用期间，可以继承它的子类
 * channelInboundHandler用于处理入站I/O事件
 * channelOutboundHandler用于处理出战I/O操作
 * 或者使用以下适配器类
 * channelInboundHandlerAdapter用于处理入站I/O事件
 * channelOutboundHandlerAdapter用于处理出站I/O操作
 * channelDuplexHandler用于处理入站和出站事件
 *
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1",10010).start();
    }

    private void start(){
        /**
         * Netty用于接收客户端请求的线程池职责如下
         * （1）接收客户端TCP连接，初始化Channel参数；
         * （2）将链路状态变更事件通知给ChannelPipeline
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            //绑定端口
            ChannelFuture f = null;
            f = b.connect().sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
