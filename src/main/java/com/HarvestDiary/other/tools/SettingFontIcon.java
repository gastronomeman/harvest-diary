package com.HarvestDiary.other.tools;

import javafx.scene.paint.Color;
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
}
