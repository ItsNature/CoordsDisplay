package gg.nature.coordsdisplay.commands;

import gg.nature.coordsdisplay.CoordsDisplay;
import gg.nature.coordsdisplay.file.Config;
import gg.nature.coordsdisplay.managers.CoordsData;
import gg.nature.coordsdisplay.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoordsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Config.PLAYER_USE_ONLY);
            return true;
        }

        Player player = (Player) sender;
        CoordsData data = CoordsDisplay.getInstance().getCoordsManager().getByUUID(player.getUniqueId());

        if(args.length == 1) {
            switch(args[0].toLowerCase()) {
                case "list":
                case "all": {
                    if(data.getAllLocations().isEmpty()) {
                        player.sendMessage(Config.NO_COORDS);
                        return true;
                    }

                    Config.LIST.forEach(line -> player.sendMessage(line.replace("<coords>", CoordsDisplay.getInstance().getCoordsManager().getCoords(data))));
                    break;
                }
                case "undisplay":
                case "unshow": {
                    if(!CoordsDisplay.getInstance().getCoordsManager().getDisplay().containsKey(player.getUniqueId())) {
                        player.sendMessage(Config.NOT_DISPLAYING);
                        return true;
                    }

                    CoordsDisplay.getInstance().getCoordsManager().getDisplay().remove(player.getUniqueId());
                    player.sendMessage(Config.UNDISPLAYING);
                    break;
                }
            }

            return true;
        }

        if(args.length == 2) {
            String name = args[1].toUpperCase();

            switch(args[0].toLowerCase()) {
                case "add":
                case "save": {
                    for(String locations : data.getAllLocations()) {
                        if(!locations.split("/")[0].equals(name)) continue;

                        player.sendMessage(Config.ALREADY_EXISTS.replace("<name>", name));
                        return true;
                    }

                    if(data.getAllLocations().size() > Config.COORDS_LIMIT) {
                        player.sendMessage(Config.LIMIT);
                        return true;
                    }

                    Location location = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());

                    data.getLocations().add(name + "/" + LocationUtils.locationToString(location));

                    player.sendMessage(Config.SAVED.replace("<name>", name));
                    break;
                }
                case "remove":
                case "delete": {
                    if(!CoordsDisplay.getInstance().getCoordsManager().coordsExist(data, name)) {
                        player.sendMessage(Config.DOESNT_EXIST.replace("<name>", name));
                        return true;
                    }

                    data.getAllLocations().forEach(locations -> {
                        if(!locations.split("/")[0].equals(name)) return;

                        data.getLocations().remove(locations);
                        player.sendMessage(Config.REMOVED.replace("<name>", name));
                    });
                    break;
                }
                case "display":
                case "show": {
                    if(!CoordsDisplay.getInstance().getCoordsManager().coordsExist(data, name)) {
                        player.sendMessage(Config.DOESNT_EXIST.replace("<name>", name));
                        return true;
                    }

                    if(CoordsDisplay.getInstance().getCoordsManager().getDisplay().containsKey(player.getUniqueId())) {
                        player.sendMessage(Config.ALREADY_DISPLAYING);
                        return true;
                    }

                    data.getAllLocations().forEach(locations -> {
                        if(!locations.split("/")[0].equals(name)) return;

                        CoordsDisplay.getInstance().getCoordsManager().getDisplay().put(player.getUniqueId(), locations);
                        player.sendMessage(Config.DISPLAYING.replace("<name>", name));
                    });
                    break;
                }
            }
            return true;
        }

        Config.USAGE.forEach(player::sendMessage);
        return false;
    }
}
