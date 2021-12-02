package com.lecturefeed;

import com.lecturefeed.utils.RunTimeParameterUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LectureFeedApplication {

	public static void main(String[] args) {
		RunTimeParameterUtils.setRunTimeArgs(args);
		SpringApplication.run(LectureFeedApplication.class, args);
	}

}
