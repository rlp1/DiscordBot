package rlp.discordbot.command;

import lombok.Getter;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import rlp.discordbot.DiscordUtil;

/**
 * @author rpl
 * @since 1.0
 */
public abstract class DiscordCommand {

    /**
     * This represents the name from the command, that the users should be send.
     * @since 1.0
     */
    @Getter
    private final String name;

    /**
     * This represents the usage from the command.
     * @since 1.0
     */
    @Getter
    private final String usage;

    public DiscordCommand(final String name, final String usage) {
        if (name == null) {
            throw new NullPointerException("name");
        }

        this.name = name;
        this.usage = usage == null || usage.isEmpty() ? DiscordUtil.EMPTY_STRING : usage;
    }

    /**
     * This method represents the function from the execute. If the command has necessary arguments and the input command
     * doesn't have this arguments, then send the "usage" variable message to the user.
     *
     * @param event the event.
     * @since 1.0
     */
    public abstract void execute(final MessageReceivedEvent event, final String[] args);
}
