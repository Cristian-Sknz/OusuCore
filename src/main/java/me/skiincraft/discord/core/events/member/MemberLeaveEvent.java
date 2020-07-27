package me.skiincraft.discord.core.events.member;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;

public class MemberLeaveEvent extends MemberEvent {

	private Guild guild;
	private Member member; 
	
	public MemberLeaveEvent(GuildMemberLeaveEvent e) {
		guild = e.getGuild();
		member = e.getMember();
	}
	
	public Guild getGuild() {
		return guild;
	}
	
	public Member getMember() {
		return member;
	}
	
}
