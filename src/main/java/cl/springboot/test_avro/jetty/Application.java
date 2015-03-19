package cl.springboot.test_avro.jetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author Daniel Gutierrez
 *
 */

@EnableAutoConfiguration
@ComponentScan
public class Application {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		SpringApplication.run(new String[] {"classpath:/META-INF/application-context.xml"}, args);
		
	}
	

}
