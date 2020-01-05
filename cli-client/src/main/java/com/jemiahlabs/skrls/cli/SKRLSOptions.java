package com.jemiahlabs.skrls.cli;

import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class SKRLSOptions {

    private static Options options;
    private static Option[] optionDefinitions = {
        Option.builder("v").longOpt("version").desc("Print the plugin version and exit").build(),
        Option.builder("h").longOpt("help").desc("Print this help message and exit").build(),
        Option.builder("l").longOpt("language").hasArg().optionalArg(true).desc("manages: add, remove, etc language plugins").hasArgs().build(),
        Option.builder("a").longOpt("analyzer").desc("analyze source files").hasArgs().build()
    };

    public static Options getInstance(){
        if(Objects.isNull(options)){
            options = new Options();
            Arrays.stream(optionDefinitions).forEach( option -> options.addOption(option));
        }
        return options;
    }
}
