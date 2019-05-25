package com.skrls.cli;

import java.util.Arrays;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLineParser;

public class App {
    public static void main(String[] args){
        OptionsRepository optionsRepository = new InMemoryOptionsRepository();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        String propertiesFile = "src/main/resources/config.properties";
        PropertiesHandler props = null;

        try{
            props = PropertiesHandler.fromFile(propertiesFile);
        }catch (IOException e){
            System.out.println("Unable to load properties file");
            System.exit(1);
        }

        try{
            CommandLine commandLine = parser.parse(optionsRepository.getOptions(), args);
            if(commandLine.getOptions().length == 0){
                System.out.println("A command or option must be specified");
            }

            if(commandLine.hasOption("h")){
                helpFormatter.printHelp("SRKLS [options] <command> [<args>]",optionsRepository.getOptions());
            }

            if(commandLine.hasOption("v")){
                String version = props.getProperty("version");
                String authors = props.getProperty("authors");
                String output = String.format("%s%nauthors: %s", version, authors);
                System.out.println(output);
            }

        }catch(ParseException parseException){
            String msg = String.format("Unable to parse command-line arguments: %s due to: %s",
                    Arrays.toString(args), parseException);
        }
    }
}
