package oz.yamyam_map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class YamYamMapApplication {

	public static void main(String[] args) {
		SpringApplication.run(YamYamMapApplication.class, args);
	}

}

