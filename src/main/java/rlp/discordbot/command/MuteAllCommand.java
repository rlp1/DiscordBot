package rlp.discordbot.command;

import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.function.Consumer;

/**
 * @author rlp
 * @since 1.0
 */
public class MuteAllCommand extends DiscordCommand {

    public MuteAllCommand() {
        super("muteall", "muteall [canal-de-voz]");
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        final boolean hasSelectedVoiceChannel = args.length > 0;
        final Consumer<VoiceChannel> voiceChannelConsumer = (voiceChannel) -> voiceChannel.getMembers().forEach(member -> {
            // @Note Not mute the member that send the command.
            if (event.getMember() == member) return;

            event.getGuild().getController().setMute(member, true).queue();
        });

        if (hasSelectedVoiceChannel) {
            final String voiceChannelName = args[0];
            final VoiceChannel selectedVoiceChannel = event.getJDA().getVoiceChannels().stream()
                    .filter(voiceChannel -> voiceChannel.getName().contains(voiceChannelName))
                    .findFirst()
                    .orElse(null);

            // @Note Check if the channel is not found.
            if (selectedVoiceChannel == null) {
                event.getChannel().sendMessage("Canal \"" + voiceChannelName + "\" não foi encontrado.").queue();
                return;
            }

            voiceChannelConsumer.accept(selectedVoiceChannel);

            event.getChannel().sendMessage("\"" + event.getAuthor().getName() + "\" mutou todos os jogadores do " +
                    "canal de voz \"" + selectedVoiceChannel.getName() + "\".").queue();
        }
        // @Note This represents that is mute the all users that are online in the discord server.
        else {
            event.getJDA().getVoiceChannels().forEach(voiceChannelConsumer);

            event.getChannel().sendMessage("\"" + event.getAuthor().getName() + "\" mutou todos os jogadores do " +
                    "servidor.").queue();
        }
    }
}
