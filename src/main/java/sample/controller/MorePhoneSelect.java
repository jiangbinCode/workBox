package sample.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.json.JSONObject;
import sample.config.MakeConfig;
import sample.config.MorePhoneMakeConfig;
import sample.model.file_make.ProductDataInfo;
import sample.model.m_p_make.MorePhoneMakeTableModel;
import sample.util.Http;
import sample.work.file_make.WorkCache;
import sample.work.m_p_make.M_P_MakeCache;

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
                loadPhoneModel(M_P_MakeCache.workData.getSelectPhone());
            } else {
                List<String> collect = M_P_MakeCache.workData.getSelectPhone().stream().filter(x -> StrUtil.containsIgnoreCase(x, text)).collect(Collectors.toList());
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
            loadPhoneModel(M_P_MakeCache.workData.getSelectPhone());
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
        MorePhoneMakeTableModel workData = M_P_MakeCache.workData;
        List<String> morePhoneItems = workData.getSelectPhone();
        for (String p : phones) {
            CheckBox cb = new CheckBox();
            if (morePhoneItems.contains(p)) cb.setSelected(true);
            cb.setText(p);
            cb.setOnMouseClicked(event -> {
                CheckBox tag = (CheckBox) event.getSource();
                workData.opsMorePhoneItems(tag.getText(), tag.isSelected());
                MorePhoneMakeConfig.selectPhoneNumText.setText(workData.getSelectPhone().size() + "");
            });
            vBox.getChildren().add(cb);
        }

        phonePlace.setContent(vBox);
    }

}
