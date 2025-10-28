package com.weiyi.demoWordle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoWordleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWordleApplication.class, args);
	}

}
