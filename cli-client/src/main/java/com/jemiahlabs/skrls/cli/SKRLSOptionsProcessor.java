package com.jemiahlabs.skrls.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jemiahlabs.skrls.context.ApplicationContext;
import org.jemiahlabs.skrls.context.Plugin;

public class SKRLSOptionsProcessor {

    ApplicationContext context;

    Map<String, OptionsConsumer<String[]>> argConsumers;

    public SKRLSOptionsProcessor(){
        this.context = initializeContext();
        argConsumers = new HashMap<>();
        initializeArgConsumers();
        context.LoaderPlugins(this::onSuccess, this::onFail);
    }

    public void process(String option, String... args){
        argConsumers.get(option).process(args);
    }

    private ApplicationContext initializeContext(){
        ApplicationContext context = ApplicationContext.GetNewInstance(new ReceiverImpl());
        return context;
    }

    private void initializeArgConsumers(){
        argConsumers.put("l", (args -> {
            List<Plugin> plugins = context.getPlugins();
            Set<String> userArgs = new HashSet<>(Arrays.asList(args));
            Function<Plugin, String> basePluginInfo = plugin -> String.format("%s  -  %s%n", plugin.getName(), plugin.getVersion());
            Function<Plugin, String> fullPluginInfo = plugin -> {
                String authors = "\n\t" + String.join("\n\t", plugin.getAuthors());
                return String.format("Name: %s%nVersion: %s%nTarget Language: %s%nDescription: %s%nAuthors: %s%n",
                              plugin.getName(),
                              plugin.getVersion(),
                              plugin.getTargetLanguaje(),
                              plugin.getDescription(),
                              authors);
            };
            Function<Plugin, String> selectedOption = userArgs.contains("all") ? fullPluginInfo : basePluginInfo;
            plugins.forEach( plugin -> System.out.println(selectedOption.apply(plugin)));
        }));
    }

    private void onSuccess(List<Plugin> plugins){
        plugins.forEach(this::onSuccess);
    }

    private void onSuccess(Plugin plugin){

    }

    private void onFail(Exception e, String message){
        System.out.println("Failed to load plugin " + message);
    }
}
