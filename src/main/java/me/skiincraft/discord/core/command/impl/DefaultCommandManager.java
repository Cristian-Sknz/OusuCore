package me.skiincraft.discord.core.command.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.skiincraft.discord.core.command.CommandExecutor;
import me.skiincraft.discord.core.command.ICommandManager;
import me.skiincraft.discord.core.command.objecs.Command;
import me.skiincraft.discord.core.command.utils.CommandTools;
import me.skiincraft.discord.core.exception.ThrowableConsumerException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultCommandManager implements ICommandManager {

    private final List<CommandExecutor> commands = new ArrayList<>();
    private final ExecutorService executor;

    public DefaultCommandManager(String name, int nThreads) {
        this.executor = Executors.newFixedThreadPool(nThreads,
                new ThreadFactoryBuilder()
                        .setNameFormat(name).build());
    }

    @Override
    public void registerCommand(CommandExecutor cmd){
        commands.add(cmd);
    }

    @Override
    public void unregisterCommand(CommandExecutor cmd) {
        commands.removeIf((cmdIf) -> cmdIf == cmd);
    }

    public List<CommandExecutor> getCommands() {
        return commands;
    }

    @Override
    public void call(Command command){
        CommandExecutor cmdExecutor = command.getCommandExecutor();
        if (cmdExecutor == null) {
            return;
        }
        executor.execute(() -> {
            try {
                cmdExecutor.execute(command.getName(), command.getArgs(), new CommandTools(command.getMessage()));
                cmdExecutor.onSuccessful(command);
            } catch (Exception e) {
                if (e instanceof ThrowableConsumerException){
                    cmdExecutor.onFailure(((ThrowableConsumerException) e).getException(), command);
                    return;
                }
                cmdExecutor.onFailure(e, command);
            }
        });
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
