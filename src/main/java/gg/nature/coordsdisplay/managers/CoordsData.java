package gg.nature.coordsdisplay.managers;

import gg.nature.coordsdisplay.CoordsDisplay;
import gg.nature.coordsdisplay.file.Config;
import gg.nature.coordsdisplay.file.ConfigFile;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class CoordsData {

    private UUID uuid;
    private List<String> locations;

    public CoordsData(UUID uuid) {
        this.uuid = uuid;
        this.locations = new ArrayList<>();
    }

    public List<String> getAllLocations() {
        return this.locations.stream().filter(punish -> !punish.equals("")).collect(Collectors.toList());
    }

    public void load() {
        ConfigFile file = CoordsDisplay.getInstance().getLocationsFile();

        if(file.get("locations." + this.uuid.toString()) != null) {
            Stream.of(file.get("locations." + this.uuid.toString()).toString().replace("[", "").
            replace("]", "").replace(" ", "").split(",")).forEach(locations::add);
        }

        CoordsDisplay.getInstance().getCoordsManager().getDataMap().put(uuid, this);
    }

    public void save() {
        ConfigFile file = CoordsDisplay.getInstance().getLocationsFile();

        file.set("locations." + this.uuid.toString(), this.locations.toString());

        file.save();

        CoordsDisplay.getInstance().getCoordsManager().getDataMap().remove(uuid);
    }
}
