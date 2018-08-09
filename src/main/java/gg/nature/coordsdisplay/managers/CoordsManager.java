package gg.nature.coordsdisplay.managers;

import gg.nature.coordsdisplay.CoordsDisplay;
import gg.nature.coordsdisplay.file.Config;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

@Getter
public class CoordsManager implements Listener {

    private Map<UUID, CoordsData> dataMap;
    private Map<UUID, String> display;

    public CoordsManager() {
        this.dataMap = new HashMap<>();
        this.display = new HashMap<>();

        new DisplayRunnable();

        Bukkit.getPluginManager().registerEvents(this, CoordsDisplay.getInstance());
    }

    public void disable() {
        Bukkit.getOnlinePlayers().forEach(player -> this.dataMap.get(player.getUniqueId()).save());

        this.dataMap.clear();
        this.display.clear();
    }

    public CoordsData getByUUID(UUID uuid) {
        return this.dataMap.get(uuid);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new CoordsData(event.getPlayer().getUniqueId()).load();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        CoordsData data = this.getByUUID(player.getUniqueId());

        if(data != null) data.save();

        this.display.remove(player.getUniqueId());
    }

    public String getCoords(CoordsData data) {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());

        data.getAllLocations().forEach(location -> {
            String name = location.split("/")[0];
            String loc = location.split("/")[1];

            joiner.add(Config.LIST_FORMAT.replace("<name>", name).replace("<location>", loc.replace("=", ", ")));
        });

        return joiner.toString();
    }

    public boolean coordsExist(CoordsData data, String name) {
        boolean exist = false;

        List<String> names = new ArrayList<>();

        for(String locations : data.getAllLocations()) {
            names.add(locations.split("/")[0]);

            if(!names.contains(name)) continue;

            exist = true;
        }

        return exist;
    }

    public class DisplayRunnable extends BukkitRunnable {

        DisplayRunnable() {
            this.runTaskTimerAsynchronously(CoordsDisplay.getInstance(), 40L, 40L);
        }

        @Override
        public void run() {
            display.keySet().forEach(uuid -> {
                String location = display.get(uuid);

                String message = Config.DISPLAY.replace("<name>", location.split("/")[0]).replace("<coords>", location.split("/")[1].replace("=", ", "));
                Bukkit.getPlayer(uuid).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            });
        }
    }
}
