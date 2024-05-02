package net.somyk.textfilter.util;

import net.fabricmc.loader.api.FabricLoader;
import org.simpleyaml.configuration.comments.format.YamlCommentFormat;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.somyk.textfilter.TextFilter.*;

public class ModConfig {

    public static final String CONFIG = "config.yml";
    public static final String REGEX = "Regex";
    public static final String WHITELIST = "whitelist";
    public static final String BLACKLIST = "blacklist";
    public static final String SIGN = "Sign";
    public static final String BOOK = "Book";
    public static final String ANVIL = "Anvil";
    public static final String ESymbol = "Empty_symbol_replacement";

    public static final String SIGN_BP = "signBypass";
    public static final String BOOK_BP = "bookBypass";
    public static final String ANVIL_BP = "anvilBypass";


    public static void registerConfigs() {
        Path path = (FabricLoader.getInstance().getConfigDir());
        Path path2 = path.resolve(Paths.get(MOD_ID));
        final YamlFile config = new YamlFile((path2.resolve( CONFIG ).toFile()).getAbsolutePath());
        try {
            if (!config.exists()) {
                config.createNewFile();
                LOGGER.info("New file has been created: {}", (path2.resolve(CONFIG).toFile()).getPath());
            } else {
                LOGGER.info("{} already exists, loading configurations...", (path2.resolve(CONFIG).toFile()).getPath());
            }
            config.loadWithComments();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        config.addDefault(
                String.join(".", REGEX, SIGN, WHITELIST),
                "[]");
        config.addDefault(
                String.join(".", REGEX, SIGN, BLACKLIST),
                "[^\\s\\u0021-\\u007e\\u0410-\\u044fії]|[ыёъэ]");

        config.addDefault(
                String.join(".", REGEX, BOOK, WHITELIST),
                "[]");
        config.addDefault(
                String.join(".", REGEX, BOOK, BLACKLIST),
                "[^\\s\\u0021-\\u007e\\u0410-\\u044fії]|[ыёъэ]");

        config.addDefault(
                String.join(".", REGEX, ANVIL, WHITELIST),
                "[]");
        config.addDefault(
                String.join(".", REGEX, ANVIL, BLACKLIST),
                "[^\\s\\u0021-\\u007e\\u0410-\\u044fії]|[ыёъэ]");
        config.addDefault(
                String.join(".", REGEX, ANVIL, ESymbol),
                "Forbidden symbol");

        config.setCommentFormat(YamlCommentFormat.PRETTY);
        config.setComment(REGEX, "To create your regex, I recommend using https://regexr.com/ and https://www.compart.com/en/unicode/block");

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

    public static boolean getBooleanValue(String key){
        Path path = (FabricLoader.getInstance().getConfigDir());
        Path path2 = path.resolve(Paths.get(MOD_ID));
        final YamlFile config = new YamlFile((path2.resolve( CONFIG ).toFile()).getAbsolutePath());
        try {
            config.loadWithComments();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return config.getBoolean(key);
    }

    public static String getStringValue(String key){
        Path path = (FabricLoader.getInstance().getConfigDir());
        Path path2 = path.resolve(Paths.get(MOD_ID));
        final YamlFile config = new YamlFile((path2.resolve( CONFIG ).toFile()).getAbsolutePath());
        try {
            config.loadWithComments();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return config.getString(key);
    }

    public static void setValue(String key, Object newValue){
        Path path = (FabricLoader.getInstance().getConfigDir());
        Path path2 = path.resolve(Paths.get(MOD_ID));
        final YamlFile config = new YamlFile((path2.resolve( CONFIG ).toFile()).getAbsolutePath());

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
