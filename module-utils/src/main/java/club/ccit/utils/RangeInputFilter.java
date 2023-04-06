package club.ccit.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: RangeInputFilter
 *
 * @author: mosaic
 * Date: 2023/3/27 16:50
 * Description:
 * Version:
 */
public class RangeInputFilter implements TextWatcher {
    private EditText editText;
    private Pattern pattern;
    private DecimalFormat decimalFormat;

    public RangeInputFilter(EditText editText) {
        this.editText = editText;
        pattern = Pattern.compile("^(([1-9]\\d{0,7})|0)(\\.\\d{0,2})?$"); // 匹配0.01-99999999.99的数字
        decimalFormat = new DecimalFormat("#.##"); // 用于格式化文本
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String input = s.toString();
        if (input.isEmpty()) return;
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            editText.setError("输入值必须在0.01和99999999.99之间");
            editText.setText(input.substring(0, s.length() - 1));
            editText.setSelection(editText.getText().length()); // 把光标移动到末尾
        } else {
            double value = Double.parseDouble(input);
            if (value < 0.01 || value > 99999999.99) {
                editText.setError("输入值必须在0.01和99999999.99之间");
                editText.setSelection(editText.getText().length()); // 把光标移动到末尾
            } else {
                editText.setError(null);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}