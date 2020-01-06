package com.jemiahlabs.skrls.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jemiahlabs.skrls.context.ApplicationContext;
import org.jemiahlabs.skrls.context.Plugin;

public class SKRLSOptionsProcessor {

    ApplicationContext context;

    Map<String, OptionsConsumer<String[]>> argConsumers;

    public SKRLSOptionsProcessor(){
        this.context = initializeContext();
        argConsumers = new HashMap<>();
        initializeArgConsumers();
        context.loaderPlugins((x)->{}, this::onFail);
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
            switch (args[0]){
                case "list":
                    List<Plugin> plugins = context.getPlugins();
                    String userArgs = args.length > 1 ? args[1] : "";
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
                    if(userArgs.equals("all")){
                        plugins.forEach( plugin -> System.out.println(fullPluginInfo.apply(plugin)));
                    } else if(userArgs.equals("")){
                        plugins.forEach( plugin -> System.out.println(basePluginInfo.apply(plugin)));
                    }else {
                        System.out.println("wrong argument " + userArgs);
                        System.out.println("usage: SKRLS -l list [all]");
                    }
                    break;

                case "add":
                    if(args.length > 1){
                        String pathToJar = args[1];
                        context.addPlugin(pathToJar, this::onSuccess, this::onFail);
                    } else {
                        System.out.println("missing argument [pathToJar]");
                        System.out.println("usage: SKRLS -l add [pathToJar]");
                    }
                    break;

                case "remove":
                    if(args.length > 1){
                        String[] nameAndVersion = args[1].split(":");
                        List<Plugin> installedPlugins = context.getPlugins();
                        List<Plugin> matches = installedPlugins.stream().filter( p -> p.getName().equals(nameAndVersion[0])).collect(Collectors.toList());
                        if(matches.size() > 1 && nameAndVersion.length == 1){
                            System.out.println("found more than 1 plugin with the same name, please specify the number of the version");
                        }else if(matches.size() > 1 && nameAndVersion.length == 2){
                            Optional<Plugin> foundPlugin = matches.stream().filter(p -> p.getVersion().equals(nameAndVersion[1])).findFirst();
                            foundPlugin.ifPresent( p -> context.removePlugin(p.getNameable()));
                            System.out.println("Plugin removed succesfully");
                        }else if(matches.size() == 1 && nameAndVersion.length == 1){
                            context.removePlugin(matches.get(0).getNameable());
                            System.out.println("Plugin removed successfully");
                        }
                    } else {
                        System.out.println("missing argument [pluginName]");
                        System.out.println("usage: SKRLS -l remove [pluginName]");
                    }
                    break;

                default:
                    System.out.println("invalid option " + args[0]);
                    System.out.println("usage: SKRLS -l list [all] | add [pathToJar] | remove [pluginName]");
            }
        }));
    }

    private void onSuccess(List<Plugin> plugins){
        plugins.forEach(this::onSuccess);
    }

    private void onSuccess(Plugin plugin){
        System.out.println("Plugin loaded successfully");
        System.out.println(plugin.getName() + " - " + plugin.getVersion());
    }

    private void onFail(Exception e){
        System.out.println("Failed to load plugin. Reason: " + e.getMessage());
    }
}
