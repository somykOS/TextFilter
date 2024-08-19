package net.somyk.textfilter.util;

import net.fabricmc.loader.api.FabricLoader;
import org.simpleyaml.configuration.comments.format.YamlCommentFormat;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.nio.file.Path;

import static net.somyk.textfilter.TextFilter.*;

public class ModConfig {

    public static final String CONFIG = MOD_ID + ".yml";
    public static final String REGEX = "Regex";
    public static final String WHITELIST = "whitelist";
    public static final String BLACKLIST = "blacklist";
    public static final String SIGN = "Sign";
    public static final String BOOK = "Book";
    public static final String ANVIL = "Anvil";

    public static final String SIGN_BP = "signBypass";
    public static final String BOOK_BP = "bookBypass";
    public static final String ANVIL_BP = "anvilBypass";

    private static final Path configDir = FabricLoader.getInstance().getConfigDir();
    private static final Path configFilePath = configDir.resolve(CONFIG);

    public static void registerConfigs() {
        final YamlFile config = new YamlFile(configFilePath.toFile().getAbsolutePath());
        try {
            if (!config.exists()) {
                config.createNewFile();
                LOGGER.info("[{}]: config has been created: {}", MOD_ID, configFilePath.toFile().getPath());
            } else {
                LOGGER.info("[{}]: loading configurations..", MOD_ID);
            }
            config.loadWithComments();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        config.addDefault(
                String.join(".", REGEX, SIGN, WHITELIST),
                "[\\u0021-\\u007e а-я іїєґ]");
        config.addDefault(
                String.join(".", REGEX, SIGN, BLACKLIST),
                "[ыъэ]");

        config.addDefault(
                String.join(".", REGEX, BOOK, WHITELIST),
                "[\\u0021-\\u007e \\n а-я іїєґ]");
        config.addDefault(
                String.join(".", REGEX, BOOK, BLACKLIST),
                "[ыъэ]");

        config.addDefault(
                String.join(".", REGEX, ANVIL, WHITELIST),
                "[\\u0021-\\u007e а-я іїєґ]");
        config.addDefault(
                String.join(".", REGEX, ANVIL, BLACKLIST),
                "[ыъэ]");

        config.setCommentFormat(YamlCommentFormat.PRETTY);
        config.setComment(REGEX,
                """
                        To create your regex, I recommend using https://regexr.com/ and https://www.compart.com/en/unicode/block\s
                        If whitelist is empty — all symbols will be deleted\s
                        You can allow all symbols by putting '.' into whitelist (whitelist: '.')""");

        config.setComment(
                String.join(".", REGEX, SIGN, WHITELIST),
                "Bypass permission: " + String.join(".", MOD_ID, SIGN_BP));
        config.setComment(
                String.join(".", REGEX, BOOK, WHITELIST),
                "Bypass permission: " + String.join(".", MOD_ID, BOOK_BP));
        config.setComment(
                String.join(".", REGEX, ANVIL, WHITELIST),
                "Bypass permission: " + String.join(".", MOD_ID, ANVIL_BP));

        try {
            config.save();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringValue(String key) {
        final YamlFile config = new YamlFile((configFilePath.toFile()).getAbsolutePath());
        try {
            config.loadWithComments();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return config.getString(key);
    }

    public static void setValue(String key, Object newValue){
        final YamlFile config = new YamlFile((configFilePath.toFile()).getAbsolutePath());

        try {
            config.loadWithComments();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        config.set(key, newValue);

        try {
            config.save();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
