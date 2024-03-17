package com.fuijitsu.thomas.internship;

import com.fuijitsu.thomas.internship.model.Observation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URI;
import java.net.URL;

@SpringBootApplication
@EnableScheduling
public class InternshipApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(InternshipApplication.class, args);
	}
}
