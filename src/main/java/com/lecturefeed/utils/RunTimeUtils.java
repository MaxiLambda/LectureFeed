package com.lecturefeed.utils;

import com.google.devtools.common.options.OptionsParser;
import com.lecturefeed.core.ServerOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.awt.*;
import java.net.URI;

@Service
public class RunTimeUtils {

    @Autowired
    Environment environment;

    private static ServerOptions serverOptions;

    public static boolean parseArgs(String[] args){
        OptionsParser parser = OptionsParser.newOptionsParser(ServerOptions.class);
        parser.parseAndExitUponError(args);
        serverOptions = parser.getOptions(ServerOptions.class);

        //if we had required options
//        if (options.host.isEmpty()) {
//            System.out.println("Usage: java -jar server.jar OPTIONS");
//            System.out.println(parser.describeOptions(Collections.emptyMap(), OptionsParser.HelpVerbosity.LONG));
//            return true;
//        }

        return false;
    }

    public static ServerOptions getServerOptions(){
        return serverOptions;
    }

    public static void openBrowser() {
        try {
            System.setProperty("java.awt.headless", "false");
            if (getServerOptions().openBrowser && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(String.format("http://localhost:%d/#/presenter", getServerOptions().serverPort)));
            }
        }catch (Exception ignore){}
    }

}
