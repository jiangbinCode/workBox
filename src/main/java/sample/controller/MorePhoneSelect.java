package sample.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.sun.deploy.uitoolkit.impl.text.FXAppContext;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.http.HttpEntity;
import org.json.JSONObject;
import sample.Main;
import sample.config.MakeConfig;
import sample.model.file_make.ProductDataInfo;
import sample.util.Http;
import sample.util.ProgressStage;
import sample.util.Util;
import sample.work.WorkCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-14 11:05
 **/
public class MorePhoneSelect {

    @FXML
    private ScrollPane phonePlace;

    @FXML
    public TextField fInput;

    @FXML
    private CheckBox onlySelect;


    private static List<String> phone = new ArrayList<>();


    @FXML
    void searchPhone(ActionEvent event) {
        String text = fInput.getText();
        if (onlySelect.isSelected()) {
            if (StrUtil.isBlank(text)) {
                loadPhoneModel(WorkCache.getWorkData().getMorePhoneItems());
            } else {
                List<String> collect = WorkCache.getWorkData().getMorePhoneItems().stream().filter(x -> StrUtil.containsIgnoreCase(x, text)).collect(Collectors.toList());
                loadPhoneModel(collect);
            }
        } else {
            if (StrUtil.isBlank(text)) {
                loadPhoneModel(phone);
            } else {
                List<String> collect = phone.stream().filter(x -> StrUtil.containsIgnoreCase(x, text)).collect(Collectors.toList());
                loadPhoneModel(collect);
            }
        }


    }

    @FXML
    void onlySelect(ActionEvent event) {
        if (onlySelect.isSelected()) {
            loadPhoneModel(WorkCache.getWorkData().getMorePhoneItems());
        } else {
            loadPhoneModel(phone);
        }
    }

    public void initialize() {
        draw();
    }


    public void draw() {
        if (CollectionUtil.isEmpty(phone)) refreshPhoneCache();
        loadPhoneModel(phone);

    }


    public static void refreshPhoneCache() {
        phone.clear();
        String respData = Http.post("/workBox/getPhone", null);
        JSONObject jsonObject = new JSONObject(respData);
        String data = jsonObject.getString("data");
        String[] split = data.split(",");
        phone.addAll(Arrays.asList(split));
    }


    public void loadPhoneModel(List<String> phones) {
        phonePlace.setContent(null);
        VBox vBox = new VBox();
        ProductDataInfo workData = WorkCache.getWorkData();
        List<String> morePhoneItems = workData.getMorePhoneItems();
        for (String p : phones) {
            CheckBox cb = new CheckBox();
            if (morePhoneItems.contains(p)) cb.setSelected(true);
            cb.setText(p);
            cb.setOnMouseClicked(event -> {
                CheckBox tag = (CheckBox) event.getSource();
                workData.opsMorePhoneItems(tag.getText(), tag.isSelected());
                MakeConfig.selectNum.setText(workData.getMorePhoneItems().size() + "");
            });
            vBox.getChildren().add(cb);
        }

        phonePlace.setContent(vBox);
    }

}
