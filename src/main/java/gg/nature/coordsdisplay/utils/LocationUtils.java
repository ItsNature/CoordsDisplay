package gg.nature.coordsdisplay.utils;

import org.bukkit.Location;

public class LocationUtils {

    public static String locationToString(Location location) {
        String worldName = location.getWorld().getName();
        String x = String.valueOf(location.getX());
        String y = String.valueOf(location.getY());
        String z = String.valueOf(location.getZ());

        return worldName + "=" + x + "=" + y + "=" + z;
    }
}
