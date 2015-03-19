package cl.springboot.test_avro.jetty.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Daniel Gutierrez
 *
 */
@Component
public class AvroSerializeService {

	@Value("${name:World}")
	private String name;

	public String getHelloMessage() {
		return "Hello " + this.name;
	}

}
