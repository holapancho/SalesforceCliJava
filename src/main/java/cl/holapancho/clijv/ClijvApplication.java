package cl.holapancho.clijv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cl.holapancho.clijv.service.LogicService;

@SpringBootApplication
public class ClijvApplication {

	@Bean
    LogicService dependency() {
        return new LogicService();
    }

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(ClijvApplication.class, args)));
	}

}
