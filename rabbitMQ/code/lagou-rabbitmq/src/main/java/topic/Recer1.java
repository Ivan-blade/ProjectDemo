package topic;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;

/**
 * @BelongsProject: lagou-rabbitmq
 * @Author: GuoAn.Sun
 * @CreateTime: 2020-08-10 15:08
 * @Description: 消息消费者1
 */
public class Recer1 {
    public static void main(String[] args) throws  Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列( 第二个参数为true：支持持久化)
        channel.queueDeclare("test_exchange_topic_queue_1",true,false,false,null);
        // 绑定路由（绑定 用户相关 的消息）
        channel.queueBind("test_exchange_topic_queue_1", "test_exchange_topic", "user.#");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };
        // 4.监听队列 true:自动消息确认
        channel.basicConsume("test_exchange_topic_queue_1", true,consumer);
    }
}
