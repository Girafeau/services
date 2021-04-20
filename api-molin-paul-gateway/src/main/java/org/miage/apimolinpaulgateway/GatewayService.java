package org.miage.apimolinpaulgateway;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@LoadBalancerClient(name = "gateway-service")
public class GatewayService {

	public static void main(String[] args) {
		SpringApplication.run(GatewayService.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("users", r -> r.path("/api/users/**").uri("lb://users-service"))
				.route("cards", r -> r.path("/api/cards/**").uri("lb://users-service"))
				.route("courses", r -> r.path("/api/courses/**").uri("lb://courses-service"))
				.route("episodes", r -> r.path("/api/episodes/**").uri("lb://courses-service"))
				.route("views", r -> r.path("/api/views/**").uri("lb://courses-service"))
				.route("purchases", r -> r.path("/api/purchases/**").uri("lb://courses-service"))
				.build();
	}

}
