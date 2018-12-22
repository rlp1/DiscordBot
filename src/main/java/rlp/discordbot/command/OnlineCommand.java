package rlp.discordbot.command;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rlp
 * @since 1.0
 */
public class OnlineCommand extends DiscordCommand {

    public OnlineCommand() {
        super("online", null);
    }

    @Override
    public void execute(final MessageReceivedEvent event, final String[] args) {
        final PrivateChannel privateChannel = event.getAuthor().openPrivateChannel().complete();
        final List<Member> members = event.getGuild().getMembers().stream().filter(member -> member.getVoiceState()
                .inVoiceChannel()).collect(Collectors.toList());

        final StringBuilder sb = new StringBuilder();
        for (final Member member : members) {
            sb.append(member.getEffectiveName()).append(", ");
        }

        // @Note Delete the last two characters that represents the append of ", ", that is appended in the last for-each
        // loop.
        sb.delete(sb.length() - 2, sb.length());
        sb.append(".");

        privateChannel.sendMessage("Há um total de \"" + members.size() + "\" usuários online no servidor.").queue();
        privateChannel.sendMessage(sb.toString()).queue();
    }
}
