package me.happy.minecraftdiscordsync.command;

import me.happy.minecraftdiscordsync.MinecraftDiscordSync;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class SyncMinecraftCommand implements CommandExecutor {

    private final MinecraftDiscordSync minecraftDiscordSync;
    private final String alphabetWithNumbers = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public SyncMinecraftCommand(MinecraftDiscordSync minecraftDiscordSync) {
        this.minecraftDiscordSync = minecraftDiscordSync;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Sorry, you have to be a player to execute this command.");
            return true;
        }

        Player player = (Player) sender;
        if (this.minecraftDiscordSync.getSyncManager().getSyncCache().asMap().containsValue(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You already have a code.");
            return true;
        }

        String code = generateRandomCode(6);

        while (this.minecraftDiscordSync.getSyncManager().getSyncCache().getIfPresent(code) != null)
            code = generateRandomCode(6);

        this.minecraftDiscordSync.getSyncManager().getSyncCache().put(code, player.getUniqueId());
        player.sendMessage(ChatColor.GREEN + "Please go to discord.gg/yourdiscord, go to #bot-commands and enter");
        player.sendMessage(ChatColor.GREEN + "!sync " + code + ", This will expire in three minutes.");

        return true;
    }

    private String generateRandomCode(int digits) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < digits; i++) {
            builder.append(alphabetWithNumbers.charAt(ThreadLocalRandom.current().nextInt(0, alphabetWithNumbers.length())));
        }

        return builder.toString();
    }
}
