package me.skiincraft.discord.core.repository;

import me.skiincraft.beans.stereotypes.RepositoryMap;
import me.skiincraft.sql.repository.Repository;

@RepositoryMap
public interface GuildRepository extends Repository<OusuGuild, Long> {
}
