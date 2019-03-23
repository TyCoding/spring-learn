package cn.tycoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-03-22
 */
@Service
public class ConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public String hello(String name) {
        //根据服务名称获取服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances("spring-cloud-producer");
        //因为只有一个实例，所以直接获取
        ServiceInstance instance = instances.get(0);
        String baseUrl = "http://" + instance.getHost() + ":" + instance.getPort() + "/producer/";
        String response = restTemplate.getForObject(baseUrl + name, String.class);
        return response;
    }
}
