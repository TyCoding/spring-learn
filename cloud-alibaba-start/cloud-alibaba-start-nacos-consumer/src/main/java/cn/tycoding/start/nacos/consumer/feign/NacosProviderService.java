package cn.tycoding.start.nacos.consumer.feign;

import cn.tycoding.start.nacos.consumer.feign.fallback.NacosProviderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 对方提供服务访问接口
 *
 * @author tycoding
 * @date 2020/6/22
 */
// `nacos-provider`和对应服务的`spring.name`相同
@FeignClient(value = "nacos-provider", fallback = NacosProviderFallback.class)
public interface NacosProviderService {

    /**
     * 和`服务消费者`Controller接口Path相同，保证`服务消费者`从`服务提供者`获取数据
     *
     * @param author 传递参数
     * @return 该Feign接口自动将数据返回
     */
    @GetMapping("/start/{author}")
    public String start(@PathVariable(value = "author") String author);
}
