package net.somyk.textfilter.util;

import net.minecraft.block.entity.SignText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.somyk.textfilter.util.ModConfig.*;

public class CheckText {

    public static SignText deleteSymbols(SignText original) {
        Text[] playersText = original.getMessages(false);

        // Compile patterns lazily (only if needed)
        Pattern whitelistPattern = compilePattern(getStringValue(String.join(".", REGEX, SIGN, WHITELIST)));
        Pattern blacklistPattern = compilePattern(getStringValue(String.join(".", REGEX, SIGN, BLACKLIST)));

        for (int i = 0; i < playersText.length; i++) {
            String line = playersText[i].getString();
            StringBuilder newLine = new StringBuilder();

            // Apply blacklist if it exists
            if (blacklistPattern != null) {
                Matcher matcher = blacklistPattern.matcher(line);
                applyFilter(matcher, whitelistPattern, newLine);
            } else {
                newLine.append(line); // No blacklist, keep the line as is
            }

            original = original.withMessage(i, Text.literal(newLine.toString()));
        }

        return original;
    }

    public static List<String> deleteSymbols(List<String> original) {

        // Compile patterns lazily (only if needed)
        Pattern whitelistPattern = compilePattern(getStringValue(String.join(".", REGEX, BOOK, WHITELIST)));
        Pattern blacklistPattern = compilePattern(getStringValue(String.join(".", REGEX, BOOK, BLACKLIST)));

        for (int i = 0; i < original.size(); i++) {
            String line = original.get(i);
            StringBuilder newLine = new StringBuilder();

            // Apply blacklist if it exists
            if (blacklistPattern != null) {
                Matcher matcher = blacklistPattern.matcher(line);
                applyFilter(matcher, whitelistPattern, newLine);
            } else {
                newLine.append(line); // No blacklist, keep the line as is
            }

            original.set(i, newLine.toString());
        }

        return original;
    }

    @Nullable
    public static String deleteSymbols(String original) {
        if(original == null || original.isEmpty()) return original;

        // Compile patterns lazily (only if needed)
        Pattern whitelistPattern = compilePattern(getStringValue(String.join(".", REGEX, ANVIL, WHITELIST)));
        Pattern blacklistPattern = compilePattern(getStringValue(String.join(".", REGEX, ANVIL, BLACKLIST)));
        String Esymbol =  getStringValue(String.join(".", REGEX, ANVIL, ESymbol));

        StringBuilder newLine = new StringBuilder();

        // Apply blacklist if it exists
        if (blacklistPattern != null) {
            Matcher matcher = blacklistPattern.matcher(original);
            applyFilter(matcher, whitelistPattern, newLine);
        } else {
            newLine.append(original); // No blacklist, keep the line as is
        }

        String result = newLine.toString();
        return result.isEmpty() ? Esymbol : result;
    }

    private static Pattern compilePattern(String regex) {
        return (regex.isEmpty() || regex.equals("[]")) ? null : Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    private static void applyFilter(Matcher matcher, Pattern whitelistPattern, StringBuilder newLine) {
        while (matcher.find()) {
            String match = matcher.group();
            if (whitelistPattern == null || !whitelistPattern.matcher(match).find()) {
                matcher.appendReplacement(newLine, "");
            }
        }
        matcher.appendTail(newLine);
    }
}
