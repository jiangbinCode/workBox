package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-18 15:29
 **/
public class MainController {


    @FXML
    void dataPackageMake(ActionEvent event) throws IOException {
        Stage secondWindow = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene scene = new Scene(root, 1500, 900);
        secondWindow.setTitle("数据包编辑");
        secondWindow.getIcons().add(new Image(
                Main.class.getResourceAsStream("/icon/zndz.jpg")));
        secondWindow.setScene(scene);
        secondWindow.show();
        Main.getPrimaryStage().close();
    }

    @FXML
    void picTool(ActionEvent event) throws IOException {
        Stage secondWindow = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/picTool.fxml"));
        Scene scene = new Scene(root, 1100, 800);
        secondWindow.setTitle("图片工具箱");
        secondWindow.getIcons().add(new Image(
                Main.class.getResourceAsStream("/icon/zndz.jpg")));
        secondWindow.setScene(scene);
        secondWindow.show();
        Main.getPrimaryStage().close();
    }

}
