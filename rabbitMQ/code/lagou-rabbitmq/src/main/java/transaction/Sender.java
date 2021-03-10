package transaction;

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

        channel.exchangeDeclare("test_transaction", "topic");
        channel.txSelect();// 开启事务
        try {
            channel.basicPublish("test_transaction", "product.price", null, "商品1-降价".getBytes());
            System.out.println(1 / 0);
            channel.basicPublish("test_transaction", "product.price", null, "商品2-降价".getBytes());

            channel.txCommit();// 提交事务（一起成功）
            System.out.println("[ 生产者 ]： 消息已全部发送！");
        }catch (Exception e){
            System.out.println("消息全部撤销！");
            channel.txRollback();// 事务回滚（一起失败）
            e.printStackTrace();
        }finally{
            channel.close();
            connection.close();
        }
    }
}
