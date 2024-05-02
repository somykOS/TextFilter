package net.somyk.textfilter;

import net.fabricmc.api.ModInitializer;

import net.somyk.textfilter.util.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextFilter implements ModInitializer {
	public static final String MOD_ID = "TextFilter";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModConfig.registerConfigs();
	}
}