package rlp.discordbot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author rlp
 * @since 1.0
 */
public class TestCommand extends DiscordCommand {

    public TestCommand() {
        super("test", null);
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("``` Test ```").queue();
    }
}
