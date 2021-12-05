package com.lecturefeed;

import com.lecturefeed.utils.RunTimeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;


@SpringBootApplication
public class LectureFeedApplication{

	public static void main(String[] args) {
		if(RunTimeUtils.parseArgs(args)){
			System.exit(1);
		}
//		RunTimeUtils.openBrowser();
		SpringApplication app = new SpringApplication(LectureFeedApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", RunTimeUtils.getServerOptions().serverPort));
		app.run(args);
	}

}
