package me.happy.minecraftdiscordsync.command;

import me.happy.minecraftdiscordsync.MinecraftDiscordSync;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SyncDiscordCommand extends ListenerAdapter {

    private final MinecraftDiscordSync minecraftDiscordSync;

    public SyncDiscordCommand(MinecraftDiscordSync minecraftDiscordSync) {
        this.minecraftDiscordSync = minecraftDiscordSync;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.startsWith("!sync")) {
            String[] args = message.split("\\s+");

            if (args.length == 1) {
                event.getChannel().sendMessage("Invalid arguments. Please do !sync <code>.").queue(newMessage -> newMessage.delete()
                        .and(event.getMessage().delete()).queueAfter(5, TimeUnit.SECONDS));
                return;
            }

            UUID uuid = this.minecraftDiscordSync.getSyncManager().getSyncCache().getIfPresent(args[1]);

            if (uuid == null) {
                event.getChannel().sendMessage("That code is not valid. Please try again.").queue(newMessage -> newMessage.delete()
                        .and(event.getMessage().delete()).queueAfter(5, TimeUnit.SECONDS));
                return;
            }

            this.minecraftDiscordSync.getSyncManager().getSyncCache().invalidate(args[1]);
            // TODO: Do your code for syncing like giving them a role, sending them a message in game, etc.
            event.getChannel().sendMessage("wow you are now synced with uuid " + uuid.toString()).queue();

        }
    }
}
