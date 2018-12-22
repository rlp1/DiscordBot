package rlp.discordbot.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author rlp
 * @since 1.0
 */
public class ClearCommand extends DiscordCommand {

    public ClearCommand() {
        super("clear", null);
    }

    @Override
    public void execute(final MessageReceivedEvent event, final String[] args) {
        final MessageEmbed messageEmbed = new EmbedBuilder()
                .setDescription("O chat foi limpo por " + event.getAuthor().getName() + ".")
                .build();

        event.getChannel().sendMessage(messageEmbed).queue();
    }
}
