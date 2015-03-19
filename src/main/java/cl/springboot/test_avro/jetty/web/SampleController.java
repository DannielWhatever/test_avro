/**
 * 
 */
package cl.springboot.test_avro.jetty.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.springboot.test_avro.jetty.service.AvroSerializeService;

@Controller
public class SampleController {

	@Autowired
	private AvroSerializeService avroSerializationService;

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String searchProject() {
		System.out.println("in conttroller");
		return "entrada";
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/helloWorld")
	@ResponseBody
	public String helloWorld() {
		return this.avroSerializationService.getHelloMessage();
	}
	
	
}
