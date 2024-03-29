package com.wizzdi.flexicore.boot.jpa.hibernate;

import com.wizzdi.flexicore.boot.base.init.FlexiCorePluginManager;
import com.wizzdi.flexicore.boot.jpa.hibernate.app.App;
import com.wizzdi.flexicore.boot.jpa.hibernate.app.TestEntity;
import com.wizzdi.flexicore.boot.jpa.hibernate.pluginA.TestEntityCreate;
import com.wizzdi.flexicore.boot.test.helper.PluginJar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.UUID;

@ExtendWith(SpringExtension.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = App.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")

public class PluginLoadingTest {

	private static final Logger logger= LoggerFactory.getLogger(PluginLoadingTest.class);

	private static final String pluginsPath;
	private static final String entitiesPath;
	private static final String PLUGIN_ID = "myPlugin";
	@Value("${flexicore.plugins}")
	private String pluginsDir;

	@Autowired
	private FlexiCorePluginManager flexiCorePluginManager;

	@Autowired
	private TestRestTemplate restTemplate;

	static{
		pluginsPath=getPluginsDir("plugins");
		entitiesPath=getPluginsDir("entities");;
		try {
			File pluginsDir = new File(pluginsPath);
			if (!pluginsDir.exists()) {
				if (!pluginsDir.mkdirs()) {
					logger.error("failed creating plugins dir");
				}
			}
			PluginJar pluginZip = new PluginJar.Builder(pluginsDir.toPath().resolve("my-plugin-1.2.3.zip"), PLUGIN_ID)
					.extension("com.wizzdi.flexicore.boot.jpa.hibernate.pluginA.PluginAService")
					.extension("com.wizzdi.flexicore.boot.jpa.hibernate.pluginA.PluginARepository")
					.extension("com.wizzdi.flexicore.boot.jpa.hibernate.pluginA.PluginAController")
					.extension("com.wizzdi.flexicore.boot.jpa.hibernate.pluginA.Config")

					.pluginVersion("1.2.3")
					.build();
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}

	}

	@DynamicPropertySource
	static void dynamicProperties(DynamicPropertyRegistry registry) throws IOException {

		registry.add("flexicore.plugins", ()->pluginsPath);
		registry.add("flexicore.entities", ()->entitiesPath);

	}

	private static String getPluginsDir(String prefix) {
		try {
			return Files.createTempDirectory(prefix).toFile().getAbsolutePath();

		}
		catch (Exception e){
			logger.error("failed getting "+prefix+" dir",e);
			return null;
		}

	}

private static final Random random=new Random();
	@Test
	public void testJpaPlugin() {
		/*String name = UUID.randomUUID().toString();
		String inheritedString = UUID.randomUUID().toString();

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 2000;
		String longText= random.ints(leftLimit, rightLimit + 1)
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		ResponseEntity<TestEntity> createdTestEntityRequest = restTemplate.postForEntity("/createTestEntity",new TestEntityCreate().setName(name).setLongText(longText).setInheritedString(inheritedString), TestEntity.class);
		Assertions.assertEquals(200,createdTestEntityRequest.getStatusCodeValue());
		TestEntity createdTestEntity = createdTestEntityRequest.getBody();
		Assertions.assertNotNull(createdTestEntity);
		logger.info("received "+createdTestEntity+" from plugin controller");
		ResponseEntity<TestEntity> fetchedTestEntityRequest = restTemplate.getForEntity("/getTestEntity/"+createdTestEntity.getId(), TestEntity.class);
		Assertions.assertEquals(200,fetchedTestEntityRequest.getStatusCodeValue());
		TestEntity fetchedTestEntity = fetchedTestEntityRequest.getBody();

		Assertions.assertNotNull(fetchedTestEntity);
		Assertions.assertNotNull(name,fetchedTestEntity.getName());
		Assertions.assertNotNull(longText,fetchedTestEntity.getLongText());
		Assertions.assertNotNull(inheritedString,fetchedTestEntity.getInheritedString());

		logger.info("received "+fetchedTestEntity+" from plugin controller");*/


	}

}
