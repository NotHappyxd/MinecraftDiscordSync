package me.happy.minecraftdiscordsync;

import me.happy.minecraftdiscordsync.command.SyncDiscordCommand;
import me.happy.minecraftdiscordsync.command.SyncMinecraftCommand;
import me.happy.minecraftdiscordsync.manager.SyncManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class MinecraftDiscordSync extends JavaPlugin {

    private SyncManager syncManager;
    private String token = "NTcwMDU5ODg5NDI3MDg3Mzgy.XL5rpA.FftCGKUQoTwp-vGnhjFlABKTA8k";

    @Override
    public void onEnable() {
        this.syncManager = new SyncManager();
        getCommand("sync").setExecutor(new SyncMinecraftCommand(this));

        try {
            JDABuilder builder = JDABuilder.createDefault(this.token);

            builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
            builder.setBulkDeleteSplittingEnabled(false);
            builder.setCompression(Compression.NONE);
            builder.setActivity(Activity.playing("The Craft"));
            builder.addEventListeners(new SyncDiscordCommand(this));

            builder.build();
        }catch (LoginException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public SyncManager getSyncManager() {
        return syncManager;
    }
}
