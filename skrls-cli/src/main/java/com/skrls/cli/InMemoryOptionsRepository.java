package com.skrls.cli;

import org.apache.commons.cli.Options;

public class InMemoryOptionsRepository implements OptionsRepository {

    private Options options = new Options();

    public InMemoryOptionsRepository(){
        options.addOption("c", "commands", false, "prints available commands.")
               .addOption("h", "help", false, "prints this help.")
               .addOption("v", "version", false, "print the version and exit.");
    }

    @Override
    public Options getOptions() {
        return options;
    }
}
