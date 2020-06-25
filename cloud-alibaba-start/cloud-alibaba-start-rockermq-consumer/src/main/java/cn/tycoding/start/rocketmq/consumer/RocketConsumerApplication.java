package cn.tycoding.start.rocketmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * RocketMQ 消息消费者
 *
 * @author tycoding
 * @date 2020/6/25
 */
@SpringBootApplication
@EnableBinding({Sink.class})
public class RocketConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketConsumerApplication.class, args);
    }

    @StreamListener("input")
    public void receiverInput(String receiveMsg) {
        System.out.println("consumer get msg: " + receiveMsg);
    }
}
