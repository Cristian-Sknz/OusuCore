package me.skiincraft.discord.core.command;

import me.skiincraft.discord.core.configuration.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public abstract class Command {

	private final String name;
	private final List<String> aliases;
	private final String usage;
	
	public Command(String name, List<String> aliases, String usage) {
		this.name = name;
		this.aliases = aliases;
		this.usage = usage;
	}
	
	public String getCommandDescription(LanguageManager language) {
		return language.getString("CommandDescription", name.toUpperCase() +"_DESCRIPTION");
	}

	public abstract void execute(Member user, String[] args, InteractChannel channel);

	public String getCommandName() {
		return name;
	}


	public List<String> getAliases() {
		return aliases;
	}

	public String getUsage() {
		return usage;
	}
	
	public LanguageManager getLanguageManager(Guild guild) {
		return new LanguageManager(guild);
	}
	
}
