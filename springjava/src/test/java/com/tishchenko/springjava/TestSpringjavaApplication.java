package com.tishchenko.springjava;

import org.springframework.boot.SpringApplication;
import tech.reliab.course.tishchenkomv.bank.springjava.SpringjavaApplication;

public class TestSpringjavaApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringjavaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
