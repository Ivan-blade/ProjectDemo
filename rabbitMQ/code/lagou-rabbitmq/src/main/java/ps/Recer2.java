package ps;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;

/**
 * @BelongsProject: lagou-rabbitmq
 * @Author: GuoAn.Sun
 * @CreateTime: 2020-08-10 15:08
 * @Description: 消息消费者2
 */
public class Recer2 {
    public static void main(String[] args) throws  Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_exchange_fanout_queue_2",false,false,false,null);
        // 绑定路由（关注）
        channel.queueBind("test_exchange_fanout_queue_2", "test_exchange_fanout", "");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("【消费者2】 = " + s);
            }
        };
        // 4.监听队列 true:自动消息确认
        channel.basicConsume("test_exchange_fanout_queue_2", true,consumer);
    }
}
