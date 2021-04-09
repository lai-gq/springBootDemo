package com.lai.demo.ActiveMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.*;

/*
 * @author uv
 * @date 2018/9/15 14:54
 *
 */
@RestController
public class ProviderController {

    //注入存放消息的队列，用于下列方法一
    @Resource
    private Queue queue;

    //注入springboot封装的工具类( 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装)
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 浏览器访问，模拟加如队列：http://localhost:8081/springboot/send?name=testMQ
     * @param name
     */
    @RequestMapping("send")
    public void send(String name) {
        System.out.println("队列参数："+name);
        //方法一：添加消息到消息队列
        jmsMessagingTemplate.convertAndSend(queue, name);
        //方法二：这种方式不需要手动创建queue，系统会自行创建名为test的队列
        //jmsMessagingTemplate.convertAndSend("test", name);
    }


    /**
     * java代码版测试发送
     * 1、编写一个测试类对ActiveMQ进行测试，首先得向pom文件中添加ActiveMQ相关的jar包
     * 2、<dependency><groupId>org.apache.activemq</groupId><artifactId>activemq-all</artifactId></dependency>
     * @throws Exception
     */
    public void testMQProducerQueue() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        //Topic topic = session.createTopic("test-topic");
        Queue queue = session.createQueue("test-queue");
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(queue);
        //7、使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello!test-queue");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }
}