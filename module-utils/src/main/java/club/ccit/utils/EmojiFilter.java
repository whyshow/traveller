package club.ccit.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: EmojiFilter
 *
 * @author: mosaic
 * Date: 2023/3/14 14:53
 * Description: 过滤Emoji特殊字符
 * Version:
 */
public class EmojiFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2, int i3) {
        Pattern pattern = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return "";
        }
        return null;
    }
}