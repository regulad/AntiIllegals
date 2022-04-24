package xyz.regulad.antiillegals;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.regulad.antiillegals.api.API;

import java.util.Map;

public class AntiIllegals extends JavaPlugin implements API, Listener {
    @Getter
    private static @Nullable AntiIllegals instance;
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
        this.metrics = new Metrics(this, 15036); // TODO: Replace this in your plugin!
        // Register events
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onInventoryOpen(final @NotNull InventoryOpenEvent event) {
        for (int index = 0; index < event.getInventory().getSize(); index++) {
            final @Nullable ItemStack item = event.getInventory().getItem(index);
            if (item != null) {
                boolean hasChanged = false;
                for (final @NotNull Map.Entry<@NotNull Enchantment, @NotNull Integer> entry : item.getEnchantments().entrySet()) {
                    if (entry.getKey().getMaxLevel() < entry.getValue()) {
                        item.addEnchantment(entry.getKey(), entry.getKey().getMaxLevel());
                    }
                    if (!hasChanged) {
                        hasChanged = true;
                    }
                }
                if (hasChanged) {
                    event.getInventory().setItem(index, item);
                }
            }
        }
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
