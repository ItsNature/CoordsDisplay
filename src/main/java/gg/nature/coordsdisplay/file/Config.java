package gg.nature.coordsdisplay.file;

import gg.nature.coordsdisplay.CoordsDisplay;

import java.util.List;

public class Config {

    public static int COORDS_LIMIT;
    public static String PLAYER_USE_ONLY;
    public static List<String> USAGE;
    public static String LIST_FORMAT;
    public static List<String> LIST;
    public static String NO_COORDS;
    public static String LIMIT;
    public static String SAVED;
    public static String REMOVED;
    public static String ALREADY_EXISTS;
    public static String DOESNT_EXIST;
    public static String DISPLAY;
    public static String ALREADY_DISPLAYING;
    public static String DISPLAYING;
    public static String NOT_DISPLAYING;
    public static String UNDISPLAYING;

    public Config() {
        ConfigFile config = CoordsDisplay.getInstance().getConfigFile();

        COORDS_LIMIT = config.getInt("COORDS_LIMIT");
        PLAYER_USE_ONLY = config.getString("PLAYER_USE_ONLY");
        USAGE = config.getStringList("COMMAND.USAGE");
        LIST_FORMAT = config.getString("COMMAND.LIST_FORMAT");
        LIST = config.getStringList("COMMAND.LIST");
        NO_COORDS = config.getString("COMMAND.NO_COORDS");
        LIMIT = config.getString("COMMAND.LIMIT");
        SAVED = config.getString("COMMAND.SAVED");
        REMOVED = config.getString("COMMAND.REMOVED");
        ALREADY_EXISTS = config.getString("COMMAND.ALREADY_EXISTS");
        DOESNT_EXIST = config.getString("COMMAND.DOESNT_EXIST");
        DISPLAY = config.getString("COMMAND.DISPLAY");
        ALREADY_DISPLAYING = config.getString("COMMAND.ALREADY_DISPLAYING");
        DISPLAYING = config.getString("COMMAND.DISPLAYING");
        NOT_DISPLAYING = config.getString("COMMAND.NOT_DISPLAYING");
        UNDISPLAYING = config.getString("COMMAND.UNDISPLAYING");
    }
}
