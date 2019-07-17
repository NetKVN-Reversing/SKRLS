package org.jemiahlabs.skrls.parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class CommandsInterpreter {

    private CommandLine cmd;
    private Options opts;

    public CommandsInterpreter(CommandLine cmd, Options opts){
        this.cmd = cmd;
        this.opts = opts;
    }

    public boolean hasOption(String option){
        return cmd.hasOption(option);
    }

    public Options getOptions(){
        return opts;
    }

    public int getNumberOfArguments(){
        return cmd.getArgList().size();
    }

    public String[] getOptionValues(String option){
        return cmd.getOptionValues(option);
    }
}
