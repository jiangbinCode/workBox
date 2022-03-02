package sample;

import cn.hutool.core.img.ImgUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.config.ConfigBasic;
import sample.util.FileUtil;
import sample.util.PictureInspect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage; //窗口
        primaryStage.getIcons().add(new Image(
                Main.class.getResourceAsStream("/icon/zndz.jpg"))); //加载窗口图标
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml")); //加载场景文件
        primaryStage.setTitle("智能店长工具箱 " + ConfigBasic.VERSION); //窗口标题 显示在窗口左上角的
        Scene scene = new Scene(root, 500, 400); //场景的大小
        scene.getStylesheets().add("/css/style.css"); //css文件

        primaryStage.setScene(scene); //窗口加载场景
        primaryStage.show(); //展示
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }


    public void test() {
        File file = new File("D:\\Documents\\WeChat Files\\wxid_vdo3vabha9mv22\\FileStorage\\File\\2022-03\\oppo1");
        List<File> files = new ArrayList<>();
        FileUtil.getRootFileSonAll(file, files);
        for (File file1 : files) {
            String mimeType = PictureInspect.getMimeType(file1.getPath());
//           if (mimeType.equals("jpeg")) {
//                ImgUtil.convert(file1, new File(file1.getPath().replace(".jpeg", "jpg")));
//            }
            assert mimeType != null;
            if (mimeType.equals("gif")) {
                ImgUtil.convert(file1, new File(file1.getPath().replace("gif", "png")));
                cn.hutool.core.io.FileUtil.del(file1);
            }
        }

    }

    public void test2() {

    }
}
