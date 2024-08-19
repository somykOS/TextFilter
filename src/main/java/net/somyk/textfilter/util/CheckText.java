package net.somyk.textfilter.util;

import net.minecraft.block.entity.SignText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Pattern;

import static net.somyk.textfilter.util.ModConfig.*;

public class CheckText {

    private static Pattern WHITELIST_SIGN = compilePattern(getStringValue(String.join(".", REGEX, SIGN, WHITELIST)));
    private static Pattern BLACKLIST_SIGN = compilePattern(getStringValue(String.join(".", REGEX, SIGN, BLACKLIST)));
    private static Pattern WHITELIST_BOOK = compilePattern(getStringValue(String.join(".", REGEX, BOOK, WHITELIST)));
    private static Pattern BLACKLIST_BOOK = compilePattern(getStringValue(String.join(".", REGEX, BOOK, BLACKLIST)));
    private static Pattern WHITELIST_ANVIL = compilePattern(getStringValue(String.join(".", REGEX, ANVIL, WHITELIST)));
    private static Pattern BLACKLIST_ANVIL = compilePattern(getStringValue(String.join(".", REGEX, ANVIL, BLACKLIST)));

    private static final ThreadLocal<StringBuilder> stringBuilderPool = ThreadLocal.withInitial(StringBuilder::new);

    public static SignText deleteSymbols(SignText original) {
        Text[] playersText = original.getMessages(false);

        for (int i = 0; i < playersText.length; i++) {
            String filteredLine = filterLine(playersText[i].getString(), WHITELIST_SIGN, BLACKLIST_SIGN);
            original = original.withMessage(i, Text.literal(filteredLine));
        }

        return original;
    }

    public static List<String> deleteSymbols(List<String> original) {
        original.replaceAll(line -> filterLine(line, WHITELIST_BOOK, BLACKLIST_BOOK));
        return original;
    }

    @Nullable
    public static String deleteSymbols(String original) {
        if (original == null || original.isEmpty()) return original;
        return filterLine(original, WHITELIST_ANVIL, BLACKLIST_ANVIL);
    }

    private static String filterLine(String line, Pattern whitelistPattern, Pattern blacklistPattern) {
        if (whitelistPattern == null) return "";

        StringBuilder newLine = stringBuilderPool.get();
        newLine.setLength(0);  // Clear the StringBuilder

        for (int i = 0; i < line.length(); i++) {
            String character = String.valueOf(line.charAt(i));
            if (whitelistPattern.matcher(character).matches() &&
                    (blacklistPattern == null || !blacklistPattern.matcher(character).matches())) {
                newLine.append(character);
            }
        }

        return newLine.toString();
    }

    private static Pattern compilePattern(String regex) {
        return (regex.isEmpty() || regex.equals("[]")) ? null
                : Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    public static void reloadPatterns() {
        WHITELIST_SIGN = compilePattern(getStringValue(String.join(".", REGEX, SIGN, WHITELIST)));
        BLACKLIST_SIGN = compilePattern(getStringValue(String.join(".", REGEX, SIGN, BLACKLIST)));
        WHITELIST_BOOK = compilePattern(getStringValue(String.join(".", REGEX, BOOK, WHITELIST)));
        BLACKLIST_BOOK = compilePattern(getStringValue(String.join(".", REGEX, BOOK, BLACKLIST)));
        WHITELIST_ANVIL = compilePattern(getStringValue(String.join(".", REGEX, ANVIL, WHITELIST)));
        BLACKLIST_ANVIL = compilePattern(getStringValue(String.join(".", REGEX, ANVIL, BLACKLIST)));
    }
}
