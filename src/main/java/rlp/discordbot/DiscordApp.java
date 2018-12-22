package rlp.discordbot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import rlp.discordbot.command.*;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author rlp
 * @since 1.0
 */
public final class DiscordApp extends ListenerAdapter {

    /**
     * DiscordCommand Pattern is a functional interface that defines the main character that the string needs to be a command,
     * then this variable add this pattern into the raw string. This also is an utility because if wants modify the
     * command patteern is only modify in this variable.
     *
     * @since 1.0
     */
    private static final Function<String, String> COMMAND_PATTERN = (s) -> "!" + s;

    /**
     * This represents the command list, that will store the all commands that will be handle.
     * @since 1.0
     */
    private static final List<DiscordCommand> COMMAND_LIST = new ArrayList<>();

    public static void main(String[] args) throws LoginException {
        // ... JDA Initializer ...
        final JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken("");
        builder.addEventListener(new DiscordApp());
        builder.buildAsync();

        // ... Commands Register ...
        COMMAND_LIST.add(new PingCommand());
        COMMAND_LIST.add(new ClearCommand());
        COMMAND_LIST.add(new GlobalCommand());
        COMMAND_LIST.add(new TestCommand());
        COMMAND_LIST.add(new MoveAllCommand());
        COMMAND_LIST.add(new KickCommand());
        COMMAND_LIST.add(new MuteCommand());
        COMMAND_LIST.add(new UnmuteCommand());
        COMMAND_LIST.add(new MuteAllCommand());
        COMMAND_LIST.add(new UnmuteallCommand());
        COMMAND_LIST.add(new OnlineCommand());
        COMMAND_LIST.add(new RegisteredCommand());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // @Note Check if the author from the messages is a bot, this can prevent that the bot send a command, and
        // answer it in a looping.
        if (event.getAuthor().isBot()) {
            return;
        }

        for (final DiscordCommand discordCommand : COMMAND_LIST) {
            final String[] inputArgs = event.getMessage().getContentRaw().split(" ");
            final String inputCommandName = inputArgs[0];

            // @Note Check if the input message is not equals with the discord command name, then continue it.
            if (!inputCommandName.equalsIgnoreCase(COMMAND_PATTERN.apply(discordCommand.getName()))) {
                continue;
            }

            final String[] commandArgs = discordCommand.getUsage().split(" ");
            int necessaryArgsLength = 0;

            for (final String commandArg : commandArgs) {
                if (commandArg.startsWith("<") && commandArg.endsWith(">")) {
                    necessaryArgsLength++;
                }
            }

            // @Note Check if the command has the necessary args length to execute the command, otherwise send to the
            // user how to use the command, with the usage message.
            if (inputArgs.length - 1 < necessaryArgsLength) {
                final MessageEmbed messageEmbed = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setDescription("Uso Inválido! Tente: " + discordCommand.getUsage() + ".")
                        .build();

                event.getChannel().sendMessage(messageEmbed).queue();
                return;
            }

            final String[] newArgs = new String[inputArgs.length - 1];

            // @Note Copy the input args without command name to a new argument array.
            System.arraycopy(inputArgs, 1, newArgs, 0, inputArgs.length - 1);

            // @Note Execute the command.
            discordCommand.execute(event, newArgs);
        }
    }
}
