package me.skiincraft.discord.core.jda;

import me.skiincraft.discord.core.OusuCore;
import me.skiincraft.discord.core.configuration.Language;
import me.skiincraft.discord.core.repository.OusuGuild;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.util.Arrays;
import java.util.Comparator;

public class GuildEvents {

    @SubscribeEvent
    public void onReady(GuildReadyEvent event){
        if (OusuCore.getGuildRepository().getById(event.getGuild().getIdLong()).isPresent()) {
            return;
        }
        OusuCore.getGuildRepository().save(new OusuGuild(event.getGuild()).setPrefix(OusuCore.getInternalSettings()
                .getDefaultPrefix())
                .setLanguage(getLanguageByRegion(event.getGuild().getRegion())));
    }

    @SubscribeEvent
    public void onJoin(GuildJoinEvent event){
        OusuCore.getGuildRepository().save(new OusuGuild(event.getGuild()).setPrefix(OusuCore.getInternalSettings()
                .getDefaultPrefix())
                .setLanguage(getLanguageByRegion(event.getGuild().getRegion())));
    }

    @SubscribeEvent
    public void onLeave(GuildLeaveEvent event){
        OusuGuild dbGuild = OusuCore.getGuildRepository().getById(event.getGuild().getIdLong()).orElse(null);
        if (dbGuild == null){
            return;
        }
        OusuCore.getGuildRepository().remove(dbGuild);
    }

    private Language getLanguageByRegion(Region region) {
        Language[] languageList = OusuCore.getLanguages().stream()
                .filter(language -> Arrays.asList(language.getRegions()).contains(region) || language.getRegions() == null)
                .sorted(Comparator.nullsLast(Comparator.comparing(language -> (language.getRegions() == null) ? null : language.getRegions()[0])))
                .toArray(Language[]::new);

        return (languageList.length != 0) ? languageList[0] : (OusuCore.getLanguages().size() == 0) ? null : OusuCore.getLanguages().get(0);
    }

}
