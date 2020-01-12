package com.jemiahlabs.skrls.cli;

import java.util.Objects;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

public class SKRLSOptions {

    private static Options options;

    public static Options getInstance(){
        if(Objects.isNull(options)){
            Option version = Option.builder("v").longOpt("version").desc("print the version and exit").build();
            Option help = Option.builder("h").longOpt("help").desc("print this help message and exit").build();
            Option plugin = Option.builder("p").longOpt("plugin").desc("manage plugins").build();
            Option add = Option.builder().longOpt("add").hasArg().argName("PLUGIN PATH").desc("use with -p option. Add a plugin").build();
            Option list = Option.builder().longOpt("list").desc("use with -p option. List installed plugins").build();
            Option remove = Option.builder().longOpt("remove").hasArg().argName("PLUGIN").desc("use with -p option. Remove an installed plugin").build();
            Option analyze = Option.builder("a").longOpt("analyze").desc("analyze source files").build();
            Option language = Option.builder().longOpt("language").hasArg().numberOfArgs(1).argName("LANGUAGE PLUGIN").desc("use with option -a. Select the plugin language").build();
            Option input = Option.builder().longOpt("input").hasArg().numberOfArgs(1).argName("FILE PATH").desc("use with option -a. Specify the input directory").build();
            Option output = Option.builder().longOpt("output").hasArg().numberOfArgs(1).argName("FILE PATH").desc("use with option -a. Specify the output directory").build();
            Option verbose = Option.builder().longOpt("verbose").desc("use with option -a. Be very verbose").build();

            options = new Options();
            OptionGroup baseOptions = new OptionGroup();
            baseOptions.addOption(version);
            baseOptions.addOption(help);
            baseOptions.addOption(plugin);
            baseOptions.addOption(analyze);
            baseOptions.addOption(verbose);

            OptionGroup pluginSubOptions = new OptionGroup();
            pluginSubOptions.addOption(add);
            pluginSubOptions.addOption(list);
            pluginSubOptions.addOption(remove);

            OptionGroup analyzeSubOptions = new OptionGroup();
            analyzeSubOptions.addOption(language);

            options.addOptionGroup(baseOptions);
            options.addOptionGroup(pluginSubOptions);
            options.addOptionGroup(analyzeSubOptions);
            options.addOption(input);
            options.addOption(output);
        }
        return options;
    }
}
