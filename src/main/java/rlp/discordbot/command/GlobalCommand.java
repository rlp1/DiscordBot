package rlp.discordbot.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author rlp
 * @since 1.0
 */
public class GlobalCommand extends DiscordCommand {

    public GlobalCommand() {
        super("global", "global <mensagem>");
    }

    @Override
    public void execute(MessageReceivedEvent event, final String[] args) {
        final StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        // @Note Delete the last character that represents a space (" "), that is added by the last for-each loop.
        sb.deleteCharAt(sb.length() - 1);

        final Role mainRole = event.getMember().getRoles().get(0);
        final MessageEmbed messageEmbed = new EmbedBuilder()
                .setAuthor(mainRole.getName() + " " + event.getAuthor().getName())
                .setDescription(sb.toString())
                .setColor(mainRole.getColor())
                .build();

        event.getJDA().getTextChannels().forEach(textChannel -> textChannel.sendMessage(messageEmbed).queue());
    }
}
