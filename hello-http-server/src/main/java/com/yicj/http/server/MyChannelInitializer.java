package com.yicj.http.server;
 
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
 
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取管道
        ChannelPipeline pipeline = ch.pipeline();
        HttpObjectAggregator httpObjectAggregator ;
        // 设置编码解码处理器
        pipeline.addLast("MyHttpResponseEncoder", new HttpResponseEncoder());
        pipeline.addLast("MyHttpRequestDecoder", new HttpRequestDecoder());
        // 设置自定义处理器
        pipeline.addLast("MyHttpServerHandler", new MyHttpServerHandler());
    }
}