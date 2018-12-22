package rlp.discordbot.command;

import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author rlp
 * @since 1.0
 */
public class MoveAllCommand extends DiscordCommand {

    public MoveAllCommand() {
        super("moveall", "moveall <canal-de-voz>");
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        // @Note Check if the player is in voice channel.
        if (!event.getMember().getVoiceState().inVoiceChannel()) {
            event.getChannel().sendMessage("Você precisa estar dentro de um canal de voz para executar esta operação.")
                    .queue();
            return;
        }

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

        // @Note Move the all players that are connected in the discord server to a selected channel.
        event.getJDA().getVoiceChannels().forEach(voiceChannel -> voiceChannel.getMembers()
                .forEach(member -> event.getGuild().getController().moveVoiceMember(member, selectedVoiceChannel).queue()));

        event.getChannel().sendMessage("\"" + event.getAuthor().getName() + "\" moveu todos os jogadores para a " +
                "sala \"" + selectedVoiceChannel.getName() + "\".").queue();
    }
}
