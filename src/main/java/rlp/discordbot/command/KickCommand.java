package rlp.discordbot.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

/**
 * @author rlp
 * @since 1.0
 */
public class KickCommand extends DiscordCommand {

    public KickCommand() {
        super("kick", "kick <nome> <motivo>");
    }

    @Override
    public void execute(final MessageReceivedEvent event, final String[] args) {
        final String username = args[0];
        final String reason = args[1];

        final List<Member> selectedMembers = event.getGuild().getMembersByName(username, false);

        // @Note Check if has selected members.
        if (selectedMembers.isEmpty()) {
            event.getChannel().sendMessage("Não há usuário com o nome \"" + username + "\".").queue();
            return;
        }

        // @Note Check if has a member with more than a 1 name.
        if (selectedMembers.size() > 1) {
            event.getChannel().sendMessage("Há mais de 1 usuário com o nome \"" + username + "\", não é possível " +
                    "completar a operação.").queue();
            return;
        }

        // @Note This represents the member that will be kicked from the discord server.
        final Member selectedMember = selectedMembers.get(0);

        final MessageEmbed messageEmbedToKickedMember = new EmbedBuilder()
                .setDescription("Desculpa, más você foi kickado pelo " +
                        "seguinte motivo \"" + reason + "\" por \"" + event.getAuthor().getName() + "\".")
                .setColor(Color.YELLOW)
                .build();

        // @Note Kick the selected member from the discord server.
        selectedMember.getUser().openPrivateChannel().complete().sendMessage(messageEmbedToKickedMember).queue();

        event.getGuild().getController().kick(selectedMember).queue();

        event.getChannel().sendMessage("\"" + event.getAuthor().getName() + "\" kickou o jogador \"" + username +
                "\" pelo motivo \"" + reason + "\".").queue();
    }
}
