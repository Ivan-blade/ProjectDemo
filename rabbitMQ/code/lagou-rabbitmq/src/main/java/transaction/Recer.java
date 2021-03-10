package transaction;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;

/**
 * @BelongsProject: lagou-rabbitmq
 * @Author: GuoAn.Sun
 * @CreateTime: 2020-08-10 15:08
 * @Description: 消息消费者
 */
public class Recer {
    public static void main(String[] args) throws  Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("test_transaction_queue",false,false,false,null);
        channel.queueBind("test_transaction_queue", "test_transaction", "product.#");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("【消费者】 = " + s);
            }
        };
        // 4.监听队列 true:自动消息确认
        channel.basicConsume("test_transaction_queue", true,consumer);
    }
}
