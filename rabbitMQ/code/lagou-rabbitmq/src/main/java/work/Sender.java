package work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
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
        channel.queueDeclare("test_work_queue",false,false,false,null);
        for(int i = 1;i<=100;i++) {
            String msg = "羊肉串 --> " + i;
            channel.basicPublish("", "test_work_queue", null, msg.getBytes());
            System.out.println("新鲜出炉：" + msg);
        }
        channel.close();
        connection.close();
    }
}
