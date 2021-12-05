package com.lecturefeed;

import com.lecturefeed.utils.RunTimeParameterUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LectureFeedApplication{

	@Value("${person.name}")
	private String name;


	public static void main(String[] args) {
		if(RunTimeParameterUtils.parseArgs(args)){
			System.exit(1);
		}
		SpringApplication.run(LectureFeedApplication.class, args);
	}





}
