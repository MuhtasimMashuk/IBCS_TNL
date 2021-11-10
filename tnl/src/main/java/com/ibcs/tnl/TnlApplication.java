package com.ibcs.tnl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients // have to be active for feign client
public class TnlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TnlApplication.class, args);
	}


	@Bean
	public WebClient.Builder getWebClientBuilder(){

		return WebClient.builder();
	}
}
