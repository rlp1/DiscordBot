package rlp.discordbot.command;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.HierarchyException;
import rlp.discordbot.DiscordUtil;

import java.util.List;

/**
 * @author rlp
 * @since 1.0
 */
public class UnmuteCommand extends DiscordCommand {

    public UnmuteCommand() {
        super("unmute", "unmute <usuário>");
    }

    @Override
    public void execute(final MessageReceivedEvent event, final String[] args) {
        final String username = DiscordUtil.joinStrings(args);
        final List<Member> selectedMembers = event.getGuild().getMembersByName(username, false);

        // @Note Check if has a member with more than a 1 name.
        if (selectedMembers.size() > 1) {
            event.getChannel().sendMessage("Há mais de 1 usuário com o nome \"" + username + "\", não é possível " +
                    "completar a operação.").queue();
            return;
        }

        // @Note This represents the selected member.
        final Member selectedMember = selectedMembers.get(0);

        try {
            event.getGuild().getController().setMute(selectedMember, false).queue();
        } catch (HierarchyException e) {
            event.getChannel().sendMessage("Você não tem permissão para desmutar o usuário \"" + username + "\".")
                    .queue();
            return;
        }

        event.getChannel().sendMessage("\"" + event.getAuthor().getName() + "\" desmutou o usuário \"" +
                selectedMember.getEffectiveName() + "\".").queue();
    }
}
