package com.linksyi.rabbitmq.demo.direct;

import com.linksyi.rabbitmq.demo.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * @author Mr.zuoyi
 * @description: 主题模式接收端
 * @createTime 2020年08月28日 15:47:00
 */

public class Receive {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();

        Channel channel = connection.createChannel();
        //申明一个自动删除非持久化队列
        String queue = channel.queueDeclare().getQueue();
        //将队列绑定交换机
        channel.queueBind(queue, "topics", "error");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(queue + ":---> " + new String(message.getBody()));
        };

        channel.basicConsume(queue, true, deliverCallback, (cancelCallback) -> {
        });
    }
}
