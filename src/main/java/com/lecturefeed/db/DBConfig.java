package com.lecturefeed.db;

import com.lecturefeed.core.HomeDirHandler;
import com.lecturefeed.utils.ObjectUtils;
import com.lecturefeed.utils.RunTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
public class DBConfig {

    @Autowired
    Environment env;

    private static Path getDatabasePathOption(){
        if(RunTimeUtils.getServerOptions().database.length() == 0) return null;
        if(!new File(RunTimeUtils.getServerOptions().database).isFile()) return null;
        return Paths.get(RunTimeUtils.getServerOptions().database);
    }

    private static Path getDefaultLectureFeedDBFilePath(String filename){
        return Paths.get(HomeDirHandler.getSafetyLectureFeedPath().toString(), filename);
    }

    private static String getURLFormatByPath(Path filePath){
        return String.format("jdbc:h2:file:%s", filePath);
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(getURLFormatByPath(ObjectUtils.getValueOrDefault(getDatabasePathOption(), getDefaultLectureFeedDBFilePath(env.getProperty("sqlite.filename")))));
        return dataSource;
    }

}
