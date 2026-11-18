package fr.exemple.alienplayer;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlienPlayerClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("alienplayer");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Alien Player chargé : tous les joueurs sont des aliens.");
    }
}
