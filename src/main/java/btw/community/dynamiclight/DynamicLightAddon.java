package btw.community.dynamiclight;

import btw.AddonHandler;
import btw.BTWAddon;
import net.fabricmc.dynamiclight.BTWLightSource;
import net.minecraft.src.Block;

public class DynamicLightAddon extends BTWAddon {
    private static DynamicLightAddon instance;
    public static int id_lightsourceinvis = 2042;
    public static Block lightsourceinvis;

    private DynamicLightAddon() {
        super("DynamicLight", "1.1.0", "Ex");
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
        addNewItems();
    }

    public static DynamicLightAddon getInstance() {
        if (instance == null)
            instance = new DynamicLightAddon();
        return instance;
    }

    public static void addNewItems()
    {
        lightsourceinvis = (new BTWLightSource(id_lightsourceinvis - 256));
    }
}
