package faewulf.itemrename.util;

import faewulf.itemrename.inter.CustomFormatting;
import faewulf.itemrename.inter.ICustomStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;


public class stringParser {

    public static Component stringToText(String str) {

        MutableComponent parsedText = Component.empty();

        //Pattern pattern = Pattern.compile("&([0123456789abcdefklmnorABCDEFKLMNOR])|&#(?:[0-9a-fA-F]{3}){1,2}");
        Pattern pattern = Pattern.compile("&([0123456789abcdefklmnorABCDEFKLMNOR]|#(?:[0-9a-fA-F]{3}){1,2})");

        //force a straight case
        Pattern forceStraightChecker = Pattern.compile("(&o)");
        boolean forceStraight = forceStraightChecker.matcher(str).find();

        //lastEnd is an indicator for the jump cursor each time matcher found a valid pattern.
        //So we can locate the substring after the last matched pattern.
        int lastEnd = 0;

        //for styles
        List<String> styles = new ArrayList<>();

        //For the original style case
        if (forceStraight) //for force reset every time switch style
            styles.add("r");
        else
            styles.add(" ");

        str = str.replaceAll("&o", "");

        Matcher matcher = pattern.matcher(str);
        //matching format code "&+"
        while (matcher.find()) {
            //string to format
            String stringAfterFormatCode = str.substring(lastEnd, matcher.start());

            //matches: &+formatcode so style will hold only the format code
            String style = matcher.group(1);

            //if string after formatcode is null, then append this style for the next string
            //Case: &2&ltest string -> test string has 2 styles: &2 and &l
            if (stringAfterFormatCode.isEmpty()) {
                styles.add(style);

                //update cursor
                lastEnd = matcher.end();
                continue;
            }

            //format string
            List<CustomFormatting> formattings = new ArrayList<>();

            //convert char to formatting then append to a list
            styles.forEach(character -> {

                //cover the first styles input
                if (Objects.equals(character, " "))
                    return;

                int colorCode = hex2Int(character);

                // If color code == -1 then it is Default Formatting,
                // != -1 is custom hex color format
                if (colorCode == -1) {
                    ChatFormatting formatStyle = ChatFormatting.getByCode(character.charAt(0));

                    if (formatStyle == null) {
                        return;
                    }

                    formattings.add(new CustomFormatting(formatStyle));
                } else {
                    formattings.add(new CustomFormatting(colorCode));
                }
            });

            //append Text to result
            parsedText.append(
                    Component.literal(stringAfterFormatCode)
                            .withStyle(style_ -> ((ICustomStyle) style_).ItemRename$withCustomFormatting(formattings.toArray(new CustomFormatting[]{})))
            );

            //after format string then reset styles
            styles.clear();

            //for force reset every time switch style
            if (forceStraight)
                styles.add("r");

            styles.add(style);

            //update cursor
            lastEnd = matcher.end();
        }

        //for remaining string
        String stringAfterFormatCode = str.substring(lastEnd);
        List<CustomFormatting> formattings = new ArrayList<>();

        //convert char to formatting then append to a list
        styles.forEach(character -> {
            //cover the first styles input
            if (Objects.equals(character, " "))
                return;

            int colorCode = hex2Int(character);
            if (colorCode == -1) {
                ChatFormatting formatStyle = ChatFormatting.getByCode(character.charAt(0));

                if (formatStyle == null) {
                    return;
                }

                formattings.add(new CustomFormatting(formatStyle));
            } else {
                formattings.add(new CustomFormatting(colorCode));
            }
        });

        //safe check if not null
        if (!stringAfterFormatCode.isEmpty()) {
            //append Text to result

            parsedText.append(
                    Component.literal(stringAfterFormatCode)
                            .withStyle(style_ -> ((ICustomStyle) style_).ItemRename$withCustomFormatting(formattings.toArray(new CustomFormatting[]{})))
            );
        }

        return parsedText;
    }

    public static int hex2Int(String hex) {
        Pattern patternHex = Pattern.compile("^#(?:[0-9a-fA-F]{3}){1,2}$");
        Matcher matcher = patternHex.matcher(hex);
        try {
            if (matcher.find()) {
                String hexValue = hex.substring(1);
                return Integer.parseInt(hexValue, 16);
            } else return -1;
        } catch (NumberFormatException e) {
            return -1;
        }

    }
}
