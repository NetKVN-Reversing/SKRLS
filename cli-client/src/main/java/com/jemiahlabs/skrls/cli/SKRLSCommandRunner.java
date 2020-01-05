package com.jemiahlabs.skrls.cli;

import java.util.Objects;
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
            CommandLine commandLine = commandLineEnvelop.get();
            if(commandLine.hasOption("v")){
                System.out.println("SKRLS-CLI v1.0.0");
            }
            if(commandLine.hasOption("h")){
                formatter.printHelp("SKRLS [options] [suboption] [<args>] ", options);
            }
            if(commandLine.hasOption("l")){
                String[] args = commandLine.getOptionValues("l");
                if(!Objects.nonNull(args)){
                    args = new String[0];
                }
                System.out.println();
                processor.process("l", args);
            }
            if(commandLine.hasOption("analyzer")){

            }
        }else{
            formatter.printHelp("SKRLS [options] [suboption] [<args>] ", options);
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
            System.out.println("Unable to parse input params: " + e.getMessage());
            System.out.println("Please check usage");
            return Optional.empty();
        }
    }

    private CommandLine parse() throws ParseException {
        return parser.parse(options, args);
    }

}
