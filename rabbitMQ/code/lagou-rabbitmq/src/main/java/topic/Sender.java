package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import util.ConnectionUtil;

/**
 * @BelongsProject: lagou-rabbitmq
 * @Author: GuoAn.Sun
 * @CreateTime: 2020-08-10 14:59
 * @Description: 消息生产者
 */
public class Sender {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明路由(路由名，路由类型，持久化)
        // topic：模糊匹配的定向分发
        channel.exchangeDeclare("test_exchange_topic", "topic", true);
        String msg = "商品降价";
        channel.basicPublish("test_exchange_topic", "product.price", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        System.out.println("[用户系统]：" + msg);
        channel.close();
        connection.close();
    }
}
