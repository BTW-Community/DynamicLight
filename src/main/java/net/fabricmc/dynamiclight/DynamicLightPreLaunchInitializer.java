package net.fabricmc.dynamiclight;

import btw.community.dynamiclight.DynamicLightAddon;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class DynamicLightPreLaunchInitializer implements PreLaunchEntrypoint {
    /**
     * Runs the PreLaunch entrypoint to register BTW-Addon.
     * Don't initialize anything else here, use
     * the method Initialize() in the Addon.
     */
    @Override
    public void onPreLaunch() {
        DynamicLightAddon.getInstance();
    }
}
