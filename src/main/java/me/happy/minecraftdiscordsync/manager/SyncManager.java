package me.happy.minecraftdiscordsync.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SyncManager {

    private final Cache<String, UUID> syncCache = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.MINUTES)
            .build();

    public Cache<String, UUID> getSyncCache() {
        return syncCache;
    }
}
