package sample.model.file_make;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-12 14:39
 **/
public class TableCheckBox {
    CheckBox checkbox = new CheckBox();

    public ObservableValue<CheckBox> getCheckBox() {
        return new ObservableValue<CheckBox>() {
            @Override
            public void addListener(ChangeListener<? super CheckBox> listener) {
            }

            @Override
            public void removeListener(ChangeListener<? super CheckBox> listener) {

            }

            @Override
            public CheckBox getValue() {
                return checkbox;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
    }

    public Boolean isSelected() {
        return checkbox.isSelected();
    }

    public void change(boolean f) {
        checkbox.setSelected(f);
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }
}
