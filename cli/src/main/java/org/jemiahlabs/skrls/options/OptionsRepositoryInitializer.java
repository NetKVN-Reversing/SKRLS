package org.jemiahlabs.skrls.options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionsRepositoryInitializer {

    private static OptionsRepository repo = new InMemoryOptionsRepository();

    public static Options run(){
        repo.insertOption(
            Option.builder("v").longOpt("version").hasArg(false).desc("print the version of this program").build(),
            Option.builder("h").longOpt("help").hasArg(false).desc("print this help and exit").build(),
            Option.builder("l").longOpt("languages").hasArg(true).desc("show the supported languages list").build(),
            Option.builder("a").longOpt("analyze").hasArg(true).desc("analyze source files").build()
        );
        return repo.retrieveOptions();
    }
}
