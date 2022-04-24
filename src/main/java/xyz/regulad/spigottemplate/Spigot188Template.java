package xyz.regulad.spigottemplate;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import xyz.regulad.spigottemplate.api.API;

public class Spigot188Template extends JavaPlugin implements API {
    @Getter
    private static @Nullable Spigot188Template instance;
    @Getter
    private @Nullable Metrics metrics;
    @Getter
    private @Nullable BukkitAudiences bukkitAudiences;

    @Override
    public void onEnable() {
        // Setup instance access
        instance = this;
        // Setup config
        this.saveDefaultConfig();
        // Setup adventure
        this.bukkitAudiences = BukkitAudiences.create(this);
        // Setup bStats metrics
        this.metrics = new Metrics(this, 13741); // TODO: Replace this in your plugin!
    }

    @Override
    public void onDisable() {
        // Discard instance access
        instance = null;
        // Discard adventure
        if (this.bukkitAudiences != null) {
            this.bukkitAudiences.close();
            this.bukkitAudiences = null;
        }
        // Discard bStats metrics
        this.metrics = null;
    }
}
