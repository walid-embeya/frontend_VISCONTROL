package es.mdef.ViscontrolAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ViscontrolApiApplication {

	public static final Logger log = LoggerFactory.getLogger(ViscontrolApiApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ViscontrolApiApplication.class, args);
	}

}
