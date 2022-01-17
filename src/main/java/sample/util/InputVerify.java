package sample.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.regex.Pattern;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-17 15:25
 **/
public class InputVerify {


    public static void InputVerifyOnlyNum(TextField... textField) {
        String pattern = "^[0-9]*$";
        Pattern r = Pattern.compile(pattern);
        for (TextField field : textField) {
            field.setTextFormatter(new TextFormatter<String>(change -> {
                String newText = change.getControlNewText();
                if (r.matcher(newText).find()) {
                    return change;
                }
                return null;
            }));
        }

    }
}
