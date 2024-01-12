package com.harvestdiary.other.tools;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class SettingFontIcon {
    public static FontIcon setSizeAndColor(Ikon icon, int size, Color color){
        FontIcon fontIcon = new FontIcon(icon);
        fontIcon.setIconSize(size);
        fontIcon.setIconColor(color);
        return fontIcon;
    }
    public static FontIcon setColor(Ikon icon, Color color){
        FontIcon fontIcon = new FontIcon(icon);
        fontIcon.setIconColor(color);
        return fontIcon;
    }
    public static Stage setStageIcon(Stage stage){
        stage.getIcons().add(new Image("/image/kls.png"));
        return stage;
    }
}
