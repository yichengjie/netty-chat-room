package com.yicj.chat.server.messagehandler.chat;

import com.yicj.chat.common.codec.Invocation;
import com.yicj.chat.common.dispatcher.MessageHandler;
import com.yicj.chat.server.message.chat.ChatRedirectToUserRequest;
import com.yicj.chat.server.message.chat.ChatSendResponse;
import com.yicj.chat.server.message.chat.ChatSendToOneRequest;
import com.yicj.chat.server.server.NettyChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatSendToOneHandler implements MessageHandler<ChatSendToOneRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, ChatSendToOneRequest message) {
        // 这里，假装直接成功
        ChatSendResponse sendResponse = new ChatSendResponse().setMsgId(message.getMsgId()).setCode(0);
        channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, sendResponse));

        // 创建转发的消息，发送给指定用户
        ChatRedirectToUserRequest sendToUserRequest = new ChatRedirectToUserRequest().setMsgId(message.getMsgId())
                .setContent(message.getContent());
        nettyChannelManager.send(message.getToUser(), new Invocation(ChatRedirectToUserRequest.TYPE, sendToUserRequest));
    }

    @Override
    public String getType() {
        return ChatSendToOneRequest.TYPE;
    }

}
