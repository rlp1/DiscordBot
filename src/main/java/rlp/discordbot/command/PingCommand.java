package rlp.discordbot.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

/**
 * @author rlp
 * @since 1.0
 */
public class PingCommand extends DiscordCommand {

    public PingCommand() {
        super("ping", null);
    }

    @Override
    public void execute(final MessageReceivedEvent event, final String[] args) {
        final long ping = event.getJDA().getPing();

        // @Note Send the ping to the player.
        event.getChannel().sendMessage(
                new EmbedBuilder().setColor(this.getColorByPing(ping)).setDescription("Ping: " + ping + "ms.").build())
                .queue();
    }

    /**
     * This method get the color by the ping statistic.
     *
     * @param ping the ping.
     * @return a color depending by the ping.
     * @since 1.0
     */
    private Color getColorByPing(final long ping) {
        if (ping <= 60L) {
            return Color.decode("#2ecc71"); // Green.
        }

        if (ping <= 100L) {
            return Color.decode("#27ae60"); // Dark Green.
        }

        if (ping <= 150L) {
            return Color.decode("#f1c40f"); // Yellow.
        }

        if (ping <= 250L) {
            return Color.decode("#f1c40f"); // Dark Yellow.
        }

        if (ping <= 500L) {
            return Color.decode("#e74c3c"); // Red.
        }

        // @Note This represents the pings above 500ms.
        return Color.decode("#c0392b"); // Dark Red.
    }
}
