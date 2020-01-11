package com.jemiahlabs.skrls.cli;

import java.util.Optional;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

public class SKRLSCommandRunner {

    Options options;
    CommandLineParser parser;
    HelpFormatter formatter;
    String[] args;
    SKRLSOptionsProcessor processor;


    public SKRLSCommandRunner(Options options, String[] args){
        this.options = options;
        this.args = args;
        this.parser = new DefaultParser();
        this.formatter = new HelpFormatter();
        this.processor = new SKRLSOptionsProcessor();
    }

    public void run(){
        Optional<CommandLine> commandLineEnvelop = parseArgs();
        if(commandLineEnvelop.isPresent()){
            CommandLine cmd = commandLineEnvelop.get();

            if(cmd.hasOption("v")){
                System.out.println("SKRLS-CLI v1.0.0");
            }

            if(cmd.hasOption("h")){
                formatter.printHelp("SKRLS", options, true);
            }

            if(cmd.hasOption("p")){
                if(!(cmd.hasOption("add") || cmd.hasOption("list") || cmd.hasOption("remove"))){
                    System.out.println("Missing suboption for -p option, please check");
                    formatter.printHelp("SKRLS", options, true);
                } else {
                    if(cmd.hasOption("add")){
                        processor.process("add", cmd.getOptionValues("add"));
                    }else if(cmd.hasOption("list")){
                        processor.process("list", args);
                    }else{
                        processor.process("remove", cmd.getOptionValues("remove"));
                    }
                }
            }

            if(cmd.hasOption("a")){
                if(!(cmd.hasOption("language") && cmd.hasOption("input") && cmd.hasOption("output"))){
                    System.out.println("Missing arguments for analyze option. Please provide plugin language, input directory and output directory, please check");
                    formatter.printHelp("SKRLS", options, true);
                } else {
                    String[] fullArgs = { cmd.getOptionValue("language"), cmd.getOptionValue("input"), cmd.getOptionValue("output") };
                    processor.process("analyze", fullArgs);
                }
            }

        }else{
            formatter.printHelp("SKRLS", options, true);
            System.exit(1);
        }
    }

    private Optional<CommandLine> parseArgs(){
        try {
            return Optional.of(parse());
        } catch (UnrecognizedOptionException e){
            System.out.println("Unrecognized option: " + e.getOption());
            System.out.println("Please check usage");
            return Optional.empty();
        } catch (ParseException e){
            System.out.println( e.getMessage());
            System.out.println("Please check usage");
            return Optional.empty();
        }
    }

    private CommandLine parse() throws ParseException {
        return parser.parse(options, args);
    }

}
