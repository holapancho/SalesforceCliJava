package cl.holapancho.clijv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cl.holapancho.clijv.service.LogicServiceImpl;

@SpringBootApplication
public class ClijvApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(ClijvApplication.class, args)));
	}

}
