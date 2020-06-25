package cn.tycoding.start.rocketmq.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * RocketMQ 消息生产者
 *
 * @author tycoding
 * @date 2020/6/25
 */
@SpringBootApplication
@EnableBinding({Source.class})
public class RocketmqProviderApplication implements CommandLineRunner {

    @Autowired
    private MessageChannel output;

    public static void main(String[] args) {
        SpringApplication.run(RocketmqProviderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        output.send(MessageBuilder.withPayload("rocketmq provider msg...").build());
    }


}
