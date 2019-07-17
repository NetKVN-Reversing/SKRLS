package org.jemiahlabs.skrls.parser;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jemiahlabs.skrls.options.OptionsRepositoryInitializer;

public class ArgumentsParser {
    private Options opts;
    private CommandLineParser parser;

    private ArgumentsParser(Options opts, CommandLineParser parser){
        this.opts = opts;
        this.parser = parser;
    }

    public static ArgumentsParser getInstance(){
        Options opts = OptionsRepositoryInitializer.run();
        CommandLineParser parser = new DefaultParser();
        return new ArgumentsParser(opts, parser);
    }

    public Map<String, Object> parse(String... args) throws ParseException {
        Map<String, Object> cmdWithOpts = new HashMap<>();
        cmdWithOpts.put("opts", opts);
        cmdWithOpts.put("cmd", parser.parse(opts, args));
        return cmdWithOpts;
    }
}
