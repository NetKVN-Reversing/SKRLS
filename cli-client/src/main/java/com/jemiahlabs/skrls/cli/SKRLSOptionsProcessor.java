package com.jemiahlabs.skrls.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jemiahlabs.skrls.context.ApplicationContext;
import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.core.Receiver;

public class SKRLSOptionsProcessor {

    ApplicationContext context;

    Map<String, OptionsConsumer<String[]>> argConsumers;

    public SKRLSOptionsProcessor(){
        this.context = initializeContext();
        argConsumers = new HashMap<>();
        initializeArgConsumers();
        context.loaderPlugins((x)->{}, (x) -> {});
    }

    public void process(String option, String... args){
        argConsumers.get(option).process(args);
    }

    private ApplicationContext initializeContext(){
        ApplicationContext context = ApplicationContext.GetNewInstance(new SoutReceiver());
        return context;
    }

    private void initializeArgConsumers(){

        argConsumers.put("add", args -> {
            String pathToJar = args[0];
            context.addPlugin(pathToJar, this::onSuccess, this::onFail);
        });

        argConsumers.put("list", args -> {
            List<Plugin> plugins = context.getPlugins();
            Function<Plugin, String> pluginInfo = plugin -> {
                String authors = "\n\t" + String.join("\n\t", plugin.getAuthors());
                return String.format("Name: %s%nVersion: %s%nTarget Language: %s%nDescription: %s%nAuthors: %s%n",
                     plugin.getName(),
                     plugin.getVersion(),
                     plugin.getTargetLanguaje(),
                     plugin.getDescription(),
                     authors);
            };
            plugins.forEach( plugin -> System.out.println(pluginInfo.apply(plugin)));
        });

        argConsumers.put("remove", args -> {
            String[] nameAndVersion = args[0].split(":");
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
        });

        argConsumers.put("analyze", args -> {
            String pluginName;
            String pluginVersion = null;
            String inputDir = args[1];
            String outputDir = args[2];
            Receiver receiver = context.getReceiver();
            if(args[0].contains(":")) {
                String[] nameAndVersion = args[0].split(":");
                pluginName = nameAndVersion[0];
                pluginVersion = nameAndVersion[1];
            } else {
                pluginName = args[0];
            }
            List<Plugin> plugins = new ArrayList<>();
            for(Plugin p: context.getPlugins()){
                if(p.getName().equals(pluginName)){
                    plugins.add(p);
                }
            }
            if(plugins.size() > 1){
                if(Objects.isNull(pluginVersion)){
                    System.out.println("there is more than one plugin with the same name, please specify the version");
                } else {
                    for(Plugin p : plugins){
                        if(p.getVersion().equals(pluginVersion)){
                            ExtractableKnowledge extractor = p.getExtractableKnowledge();
                            Producer producer = new ProducerImpl(new ChannelImpl(receiver));
                            extractor.extractKDM(producer,inputDir, outputDir);
                        }
                        break;
                    }
                }
            } else if(plugins.size() == 1){
                ExtractableKnowledge extractor = plugins.get(0).getExtractableKnowledge();
                Producer producer = new ProducerImpl(new ChannelImpl(receiver));
                extractor.extractKDM(producer,inputDir, outputDir);
            } else {
                System.out.println("plugin " + pluginName + "not found");
            }
        });
    }

    private void onSuccess(Plugin plugin){
        System.out.println("Plugin loaded successfully");
        System.out.println(plugin.getName() + " - " + plugin.getVersion());
    }

    private void onFail(Exception e){
        System.out.println("Failed to load plugin. Reason: " + e.getMessage());
    }
}
