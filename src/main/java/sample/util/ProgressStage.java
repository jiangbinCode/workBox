package sample.util;

import cn.hutool.core.util.StrUtil;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-13 10:03
 **/
public class ProgressStage {
    private Stage stage;
    private Task<?> work;

    private ProgressStage() {
    }

    /**
     * 创建
     *
     * @param parent
     * @param work
     * @param ad
     * @return
     */
    public static ProgressStage of(Stage parent, Task<?> work, String ad) {
        ProgressStage ps = new ProgressStage();
        ps.work = Objects.requireNonNull(work);
        ps.initUI(parent, ad);
        return ps;
    }

    /**
     * 显示
     */
    public Stage show() {
        new Thread(work).start();
        stage.show();
        return stage;
    }

    private void initUI(Stage parent, String ad) {
        stage = new Stage();
        stage.initOwner(parent);
        // style
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        // message
        Label adLbl = new Label(ad);
        adLbl.setTextFill(Color.BLUE);
        // progress
        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setProgress(-1);
        indicator.progressProperty().bind(work.progressProperty());
        // pack
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setBackground(Background.EMPTY);
        vBox.getChildren().addAll(indicator, adLbl);
        // scene
        Scene scene = new Scene(vBox);
        scene.setFill(null);
        stage.setScene(scene);
        stage.setWidth(ad.length() * 8 + 10);
        stage.setHeight(100);
        // show center of parent
        double x = parent.getX() + (parent.getWidth() - stage.getWidth()) / 2;
        double y = parent.getY() + (parent.getHeight() - stage.getHeight()) / 2;
        stage.setX(x);
        stage.setY(y);
        // close if work finish
        work.setOnSucceeded(e -> {
            stage.close();
        });
    }
}
