package com.HarvestDiary.UiController.MainUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;

public class CalendarUIController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

//    整个界面的盒子
    @FXML
    VBox vContainer;

//    切换月份和显示当前月份和年份的盒子
    @FXML
    VBox titleBar;

//    切换为上个月的按钮
    @FXML
    Button PreviousMouth;

//    切换为下个月的按钮
    @FXML
    Button NextMouth;

//    显示当前年份的label
    @FXML
    Label yearLabel;

//    显示当前月份的label
    @FXML
    Label monthLabel;

//     日历的pane
    @FXML
    GridPane calenderGrid;

//    添加记录的pane
    @FXML
    GridPane markerInfoBar;

//    添加记录的标题
    @FXML
    Label addRecord;

//    添加记录中记录名的label
    @FXML
    Label nameLabel;

//    添加记录中记录名的地方
    @FXML
    TextField markerNameField;

//    添加颜色的label
    @FXML
    Label colorLabel;

//    添加颜色中的颜色选择器
    @FXML
    ColorPicker markerColorPicker;

//    设置记录为日记类型的单选框
    @FXML
    RadioButton addRadioBdiary;

//    设置记录为待做类型的单选框
    @FXML
    RadioButton addRadioBDo;

//    提交记录的按钮
    @FXML
    Button userFinishButton;

//    错误信息提醒
    @FXML
    Label errorText;

//    关闭添加记录pane的label
    @FXML
    Label markerInfoClose;

//    修改板块的gridPane;
    @FXML
    GridPane editRecord;

//    编辑记录的标题label
    @FXML
    Label editRecordLabel;

//    编辑记录板块下的记录名label
    @FXML
    Label nameEditLabel;

//    编辑记录板块下的记录名textField
    @FXML
    TextField markerEditNameField;

//    编辑记录板块下的颜色设置标题label
    @FXML
    Label colorEditLabel;

//    编辑记录板块下的颜色设置器
    @FXML
    ColorPicker markerEditColorPicker;

//    编辑记录板块下的选择记录为日记类型
    @FXML
    RadioButton editRadioBdiary;

//    编辑记录板块下的选择记录为待做类型
    @FXML
    RadioButton editRadioBDo;

//    编辑记录板块下的确定修改按钮
    @FXML
    Button userEditButton;

//    编辑记录板块下的删除记录按钮
    @FXML
    Button  userDeleteButton;

//    编辑记录板块下的错误信息提醒
    @FXML
    Label errorEditText;

//    编辑记录板块下的关闭编辑记录板块label
    @FXML
    Label markerEditInfoClose;


//    月和星期的数组(英文版)
    String[]mouthsArray={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    String[]daysArray={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

//    月和星期的数组(中文版)
    String[]mouthsArrayChinese={"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
    String[]daysArrayChinese={"周一","周二","周三","周四","周五","周六","周天"};
//    获得日期
    Date cDate=new Date();

//    在日期对象中获取当前时间切割后获得当前的日期，月份，年份
    String[] cDateSplit=cDate.toString().split(" ");
    String cDay=cDateSplit[0];
    int cDayAsNum=Integer.parseInt(cDateSplit[2]);
    String cMonth=cDateSplit[1];
    String cYear=cDateSplit[5];

//    创建两个字符串来确定月份的开始的那天和结束那天
    String dayCMonthBegan="";
    String dayCMonthEnded="";



}
