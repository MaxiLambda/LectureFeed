package com.lecturefeed;

import com.lecturefeed.utils.RunTimeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;


@SpringBootApplication
@EnableScheduling
public class LectureFeedApplication{

	public static void main(String[] args) {
		if(RunTimeUtils.parseArgs(args)){
			System.exit(1);
		}
		SpringApplication app = new SpringApplication(LectureFeedApplication.class);
		int port = Integer.parseInt(System.getenv("PORT"));
		if (System.getenv("PORT").isEmpty())
		{
			port = RunTimeUtils.getServerOptions().serverPort;
		}
		app.setDefaultProperties(Collections.singletonMap("server.port", port));
		app.run(args);
	}
}
