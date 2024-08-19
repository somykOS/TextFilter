package net.somyk.textfilter;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.somyk.textfilter.command.ReloadCommand;
import net.somyk.textfilter.util.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextFilter implements ModInitializer {
	public static final String MOD_ID = "textfilter";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModConfig.registerConfigs();
		CommandRegistrationCallback.EVENT.register(ReloadCommand::register);
	}
}