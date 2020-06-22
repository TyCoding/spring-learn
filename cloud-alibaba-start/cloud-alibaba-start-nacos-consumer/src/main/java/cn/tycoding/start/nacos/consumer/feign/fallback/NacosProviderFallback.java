package cn.tycoding.start.nacos.consumer.feign.fallback;

import cn.tycoding.start.nacos.consumer.feign.NacosProviderService;
import org.springframework.stereotype.Component;

/**
 * Sentinel 服务熔断机制
 *
 * @author tycoding
 * @date 2020/6/22
 */
@Component
public class NacosProviderFallback implements NacosProviderService {

    @Override
    public String start(String author) {
        return "request error, this from sentinel";
    }
}
