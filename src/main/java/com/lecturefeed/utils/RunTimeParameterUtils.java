package com.lecturefeed.utils;

import com.google.devtools.common.options.OptionsParser;
import com.lecturefeed.core.ServerOptions;
import java.util.Map;


public class RunTimeParameterUtils {

    private static Map<String, Object> serverOptions;

    public static boolean parseArgs(String[] args){
        OptionsParser parser = OptionsParser.newOptionsParser(ServerOptions.class);
        parser.parseAndExitUponError(args);
        ServerOptions options = parser.getOptions(ServerOptions.class);
        serverOptions = options.asMap();

        //if we had required options
//        if (options.host.isEmpty()) {
//            System.out.println("Usage: java -jar server.jar OPTIONS");
//            System.out.println(parser.describeOptions(Collections.emptyMap(), OptionsParser.HelpVerbosity.LONG));
//            return true;
//        }

        return false;
    }

    public Map<String, Object> getServerOptions(){
        return serverOptions;
    }

}
