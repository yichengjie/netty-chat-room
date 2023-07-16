package com.yicj.http.server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
 
 
public class MyHttpServer {
    public static void main(String[] args) throws Exception{
        // boss工作组
        NioEventLoopGroup bossGroup = null;
        // worker工作组
        NioEventLoopGroup workerGroup = null;
        try {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
 
            // 设置启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 配置启动对象
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyChannelInitializer());
            System.out.println("http 服务器已就绪");
            // 同步绑定端口
            ChannelFuture cf = bootstrap.bind(8000).sync();
 
            // 绑定监听关闭状态
            cf.channel().closeFuture().sync();
        } finally {
            // 关闭资源
            if (workerGroup != null){
                workerGroup.shutdownGracefully();
            }
            if (bossGroup != null){
                bossGroup.shutdownGracefully();
            }
        }
 
    }
 
}