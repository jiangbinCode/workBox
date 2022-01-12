package sample.util;

import javafx.scene.control.Alert;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-12 10:29
 **/
public class Util {


    public static void msg(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set(title);
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
    }

}
