package com.ljcx.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 配置了自定义数据源，一定要排除自动注入数据源，否则会导致相互引用问题！！！
 */
@ComponentScan({"com.ljcx.*"})
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class CodeApplication {

//    @Value("${netty.port}")
//    private int port;
//
//    @Value("${netty.url}")
//    private String url;
//
//    @Autowired
//    private EchoServer echoServer;

    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        ChannelFuture future = echoServer.start(url,port);
//        Runtime.getRuntime().addShutdownHook(new Thread(){
//            @Override
//            public void run(){
//                echoServer.destroy();
//            }
//        });
//
//        //服务端管道关闭的监听器并同步阻塞，直到channel关闭，线程才会往下执行，结束进程
//        future.channel().closeFuture().syncUninterruptibly();
//    }
}
