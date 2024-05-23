package faewulf.itemrename.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class stringParser {
    public static Text stringToText(String str) {

        MutableText parsedText = Text.empty();

        Pattern pattern = Pattern.compile("&([0123456789abcdefklmnorABCDEFKLMNOR])");
        Matcher matcher = pattern.matcher(str);

        //lastEnd is an indicator for the jump cursor each time matcher found a valid pattern.
        //So we can we locate the substring after the last matched pattern.
        int lastEnd = 0;
        //for styles
        List<Character> styles = new ArrayList<>();
        //For the original style case
        styles.add(' ');

        //matching format code "&+"
        while (matcher.find()) {

            //string to format
            String stringAfterFormatCode = str.substring(lastEnd, matcher.start());

            //matches: &+formatcode so style will hold only the format code
            char style = matcher.group(1).charAt(0);

            //if string after formatcode is null then append this style for the next string
            //Case: &2&ltest string -> test string has 2 style &2 and &l
            if (stringAfterFormatCode.isEmpty()) {

                styles.add(style);

                //update cursor
                lastEnd = matcher.end();
                continue;
            }

            //format string
            List<Formatting> formattings = new ArrayList<>();

            //convert char to formatting then append to a list
            styles.forEach(character -> {

                //cover the first styles input
                if (character == ' ')
                    return;

                Formatting formatStyle = Formatting.byCode(character);
                if (formatStyle == null) {
                    return;
                }

                formattings.add(formatStyle);
            });

            //append Text to result
            parsedText.append(Text.literal(stringAfterFormatCode).styled(style_ -> style_.withFormatting(formattings.toArray(new Formatting[]{}))));

            //after format string then reset styles
            styles.clear();
            styles.add(style);

            //update cursor
            lastEnd = matcher.end();
        }

        //for remaining string
        String stringAfterFormatCode = str.substring(lastEnd);
        List<Formatting> formattings = new ArrayList<>();

        //convert char to formatting then append to a list
        styles.forEach(character -> {
            //cover the first styles input
            if (character == ' ')
                return;
            Formatting formatStyle = Formatting.byCode(character);
            if (formatStyle == null) {
                return;
            }
            formattings.add(formatStyle);
        });

        //safe check if not null
        if (!stringAfterFormatCode.isEmpty())
            //append Text to result
            parsedText.append(Text.literal(stringAfterFormatCode).styled(style_ -> style_.withFormatting(formattings.toArray(new Formatting[]{}))));

        return parsedText;
    }
}
