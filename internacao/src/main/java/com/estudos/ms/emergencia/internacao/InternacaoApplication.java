package com.estudos.ms.emergencia.internacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InternacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternacaoApplication.class, args);
	}

}
