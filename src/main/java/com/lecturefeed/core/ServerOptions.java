package com.lecturefeed.core;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class ServerOptions extends OptionsBase {

    @Option(
            name = "help",
            abbrev = 'h',
            help = "Prints usage info.",
            defaultValue = "true"
    )
    public boolean help;

    @Option(
            name = "debug",
            abbrev = 'd',
            help = "Debug mode",
            category = "startup",
            defaultValue = "true"
    )
    public boolean debug;

    @Option(
            name = "openBrowser",
            abbrev = 'o',
            help = "start default browser",
            category = "startup",
            defaultValue = "true"
    )
    public boolean openBrowser;



}