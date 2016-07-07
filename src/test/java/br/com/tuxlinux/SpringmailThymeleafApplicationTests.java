package br.com.tuxlinux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringmailThymeleafApplication.class)
@WebAppConfiguration
public class SpringmailThymeleafApplicationTests {

	@Test
	public void contextLoads() {
	}

}
