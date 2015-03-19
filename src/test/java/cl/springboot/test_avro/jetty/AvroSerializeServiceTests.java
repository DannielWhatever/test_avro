/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.springboot.test_avro.jetty;



import java.io.File;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cl.springboot.test_avro.jetty.domain.User;
import cl.springboot.test_avro.jetty.service.AvroSerializeService;

/**
 * 
 * @author Daniel Gutierrez
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AvroSerializeServiceTests {

	private static final Logger log = LoggerFactory.getLogger(AvroSerializeServiceTests.class);
	
	@Autowired
	private AvroSerializeService avroSerializationService;
	
	@Test
	public void should_serialize_and_deserialize_with_apache_avro() throws Exception {
		log.info(avroSerializationService.getHelloMessage());

		/**
		 * new user with constructor, more fast
		 */
		User user1 = new User(1, "Danniel", "Colo Colo", 65.2);

		/**
		 * new user with Builder, apply validations before serialize
		 */
		User user2 = User.newBuilder()
					.setId(2)
					.setNickname("Whatever")
					.setSoccerTeam("Lota Swager")
					.setWeight(100)
					.build();
		
		/**
		 * Serialize objects using SpecificDatumWriter, is also possible use of GenericDatumReader
		 * 
		 * To generate the Class Autogenerated by Avro use the next command:
		 * 		java -jar "C:\avro_tools_folder\avro-tools-1.7.7.jar" compile schema user.avsc .
		 */
		DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
		DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
		dataFileWriter.create(user1.getSchema(), new File("users.avro"));
		dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.close();
		
		File file = new File("users.avro");
		Assert.assertTrue(file.exists());
		
		/**
		 * Deserialize users from disk, and show using the toString method override by avro
		 */
		DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
		DataFileReader<User> dataFileReader = new DataFileReader<User>(file, userDatumReader);
		User user = null;
		while (dataFileReader.hasNext()) {
			user = dataFileReader.next(user);
			log.info(user.toString());
		}
		dataFileReader.close();

	}

}
