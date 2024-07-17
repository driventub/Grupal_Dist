package com.distribuida.author;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@Component
@Configuration
@EnableDiscoveryClient
public class AuthorsLifeCycle {

    @Value("${spring.cloud.consul.host:localhost}")
    private String consulHost;

    @Value("${spring.cloud.consul.port:8500}")
    private int consulPort;

    @Value("${server.port}")
    private int port;


    private ConsulServiceRegistry consulServiceRegistry;
    private  ConsulDiscoveryProperties properties;
    private  TtlScheduler ttlScheduler;
    private final HeartbeatProperties heartbeatProperties;

    private String serviceId;



    @PostConstruct
    public void init() throws UnknownHostException {
        System.out.println("*************** AuthorsLifeCycle init ***************");

        serviceId = UUID.randomUUID().toString();

        var ipAddress = InetAddress.getLocalHost();

        properties.setInstanceId(serviceId);
        properties.setPreferIpAddress(true);
        properties.setIpAddress(ipAddress.getHostAddress());
        properties.setPort(port);

        List<String> tags = List.of(
                "traefik.enable=true",
                "traefik.http.routers.app-authors.rule=PathPrefix(`/app-authors`)",
                "traefik.http.routers.app-authors.middlewares=app-authors",
                "traefik.http.middlewares.app-authors.stripPrefix.prefixes=/app-authors"
        );
        properties.setTags(tags);

        ConsulRegistration registration = ConsulRegistration.registration(properties);
        consulServiceRegistry.register(registration);

        if (properties.isRegisterHealthCheck()) {
            ttlScheduler.add(registration);
        }
    }

    @PreDestroy
    public void stop() {
        System.out.println("*************** AuthorsLifeCycle stop ***************");
        consulServiceRegistry.deregister(ConsulRegistration.registration(properties));
        if (properties.isRegisterHealthCheck()) {
            ttlScheduler.remove(ConsulRegistration.registration(properties));
        }
    }
}