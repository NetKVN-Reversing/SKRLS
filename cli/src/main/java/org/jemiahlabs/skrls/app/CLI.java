package org.jemiahlabs.skrls.app;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jemiahlabs.skrls.parser.ArgumentsParser;
import org.jemiahlabs.skrls.parser.CommandsInterpreter;
import org.jemiahlabs.skrls.utils.Utils;

public class CLI {

    private CommandsInterpreter interpreter;

    private CLI(CommandsInterpreter interpreter){
        this.interpreter = interpreter;
    }

    public static CLI getInstance(String... args){
        Map<String, Object> cmdWithOpts = parse(args);
        CommandLine cmd = (CommandLine) cmdWithOpts.get("cmd");
        Options opts = (Options) cmdWithOpts.get("opts");
        return new CLI(new CommandsInterpreter(cmd, opts));
    }

    //TODO process option arguments
    public void run(){
        if(interpreter.hasOption("v")){
            System.out.println(Utils.APP_VERSION);
            System.exit(0);
        }

        if(interpreter.hasOption("h") || interpreter.getNumberOfArguments() == 0){
            new HelpFormatter().printHelp( "SKRLS [options] <command> [<args>] ",interpreter.getOptions());
            System.exit(0);
        }
    }

    private static Map<String, Object> parse(String... args){
        Map<String, Object> cmdWithOpts = new HashMap<>();
        try{
            cmdWithOpts = ArgumentsParser.getInstance().parse(args);
        }catch(ParseException e){
            System.out.println(e.getMessage());
        }
        return cmdWithOpts;
    }
}
