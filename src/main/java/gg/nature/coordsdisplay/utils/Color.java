package gg.nature.coordsdisplay.utils;

import org.bukkit.ChatColor;

public class Color {

    public static String translate(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
