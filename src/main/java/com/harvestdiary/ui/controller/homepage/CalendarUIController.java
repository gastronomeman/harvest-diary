package com.harvestdiary.ui.controller.homepage;

import com.harvestdiary.other.tools.HowManyDaysInTheMonth;
import com.harvestdiary.other.tools.OperationalDocument;
import com.harvestdiary.other.tools.RecordMarker;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CalendarUIController extends Application implements HowManyDaysInTheMonth {

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
    HBox titleBar;

//    切换为上个月的按钮
    @FXML
    ImageView previousMouth;

//    切换为下个月的按钮
    @FXML
    ImageView nextMouth;

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

    @FXML
    HBox menuHbox;

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

    @FXML
    public void addMarker(){
//        获得选中的日期中的数据
        String day=currentDayLabel.getText();
        String month=cMonth;
        String year=cYear;
        String name=markerNameField.getText();
        String color=String.valueOf(markerColorPicker.getValue());
        boolean isDiary=addRadioBdiary.isSelected();
        boolean isTodo=addRadioBDo.isSelected();

        if (name.contains(",")||name.contains("，")){
            errorText.setText("记录名空间不足,请修改记录名");
            errorText.setVisible(true);
        }
        else {
            errorText.setVisible(false);

            RecordMarker r1=new RecordMarker(name,day,month,year,color,isDiary,isTodo);

            r1.addRecordToFile();
            rcordMarkers.add(r1);
            calenderGrid.getChildren().clear();
            setCalender(cDay,cDayAsNum,cMonth,cYear);

            markerNameField.setText("");
            markerColorPicker.setValue(Color.web("#ffffff"));
            addRadioBdiary.setSelected(false);
            addRadioBDo.setSelected(false);
        }
    }

    @FXML
    public void editMarker(){
//        获取当前的信息，即已经修改后的数据
        String day=currentDayLabel.getText();
        String month=cMonth;
        String year=cYear;
        String name=markerEditNameField.getText();
        String color=String.valueOf(markerEditColorPicker.getValue());
        boolean isDiary=editRadioBdiary.isSelected();
        boolean isTodo=editRadioBDo.isSelected();

        if (name.contains(",")||name.contains("，")){
            errorEditText.setText("记录名空间不足,请修改记录名");
            errorEditText.setVisible(true);
        }
        else {
            errorEditText.setVisible(false);
            deleteMarker();
            RecordMarker r1=new RecordMarker(name,day,month,year,color,isDiary,isTodo);
            r1.addRecordToFile();
            rcordMarkers.add(r1);

            calenderGrid.getChildren().clear();
            setCalender(cDay,cDayAsNum,cMonth,cYear);
        }
    }

    @FXML
    public void deleteMarker(){
        List<String> fileContents=new ArrayList<>();
        try {
            File f=new File("HarvestDiary/record.csv");
            Scanner readFile=new Scanner(f);
            while (readFile.hasNextLine()){
                String currentLine=readFile.nextLine();
                String[] currentLineSplit=currentLine.split(",");
                String day=currentLineSplit[1];
                String month=currentLineSplit[2];
                String year=currentLineSplit[3];
                String name=currentLineSplit[0];
                System.out.println(day+month+year+name);
                if (!(currentRcordMarker.getName().equals(name) && currentRcordMarker.getDay().equals(day) && currentRcordMarker.getMonth().equals(month) && currentRcordMarker.getYear().equals(year))){
                    fileContents.add(currentLine);
                }
                else {
                    rcordMarkers.remove(currentRcordMarker);
                }
            }

            FileWriter fw=new FileWriter("");
            for (String filewContent: fileContents){
                fw.write(filewContent+"\n");
            }
            fw.close();
        }
        catch (Exception e){
            calenderGrid.getChildren().clear();
            setCalender(cDay,cDayAsNum,cMonth,cYear);
        }
    }


//    月和星期的数组(英文版)
    String[] monthsArray ={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    String[]daysArray={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

//    月和星期的数组(中文版)
    String[] monthsArrayChinese ={"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
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

//    创建一个vbox和label用来确定用户在日历中选中的日期
//    我们称之为节点
    VBox currentDayNote;
    Label currentDayLabel;

//    创建一个list用来存放是日记类型的记录
    ArrayList<RecordMarker> rcordMarkers=new ArrayList<>();

//    初始化当前被点击的日记类型记录
    RecordMarker currentRcordMarker;


    //点击下一页发生的事情
    @FXML
    private void incrementMonth(){
//    先清空当前网格的内容
        calenderGrid.getChildren().clear();

//    找到上个月结束那天的下一天开始遍历日期数组
        for (int i=0;i<daysArray.length;i++){
            if (daysArray[i].equals(dayCMonthEnded)){
//                尝试将星期几切换到后一天，如果切换失败超出数组范围则说明下一天是星期一
                try {
                    cDay=daysArray[i+1];
                }
                catch (Exception e)
                {
                    cDay=daysArray[0];
                }
                cDayAsNum=1;
            }
        }
        String newMonth="";
        for (int j = 0; j< monthsArray.length; j++){
            if (monthsArray[j].equals(cMonth)){
                try {
                    newMonth= monthsArray[j+1];
                }
                catch (Exception e){
                    newMonth= monthsArray[0];
                }
            }
        }
        cMonth=newMonth;

        if (cMonth.equals("Jan")){
            cYear=String.valueOf(Integer.parseInt(cYear)+1);
        }

        cDayAsNum=HowManyDaysInTheMonth.howManyDays(cMonth,cYear);

        setCalender(cDay,cDayAsNum,cMonth,cYear);
    }
    //点击上一页发生的事情
    @FXML
    private void decrementMonth(){
        //    先清空当前网格的内容
        calenderGrid.getChildren().clear();

        for (int i=0;i<daysArray.length;i++){
            if (daysArray[i].equals(dayCMonthBegan)){
                try {
                    cDay=daysArray[i-1];
                }
                catch (Exception e){
                    cDay=daysArray[6];
                }
            }
        }
        String newMonth="";
        for (int j = 0; j< monthsArray.length; j++){
            if (monthsArray[j].equals(cMonth)){
                try {
                    newMonth= monthsArray[j-1];
                }
                catch (Exception e){
                    newMonth= monthsArray[11];
                }
            }
        }
        cMonth=newMonth;

        if (cMonth.equals("Dec")){
            cYear=String.valueOf(Integer.parseInt(cYear)-1);
        }

        int dayLimit=HowManyDaysInTheMonth.howManyDays(newMonth,cYear);
        cDayAsNum=dayLimit;

        setCalender(cDay,cDayAsNum,cMonth,cYear);
    }

    private void readRcordMarkerCSV(){
        try {
            File f=new File("HarvestDiary/record.csv");
            Scanner readFile=new Scanner(f);
            while (readFile.hasNextLine()){
                String currentLine=readFile.nextLine();
                String[] currentLineSplit=currentLine.split(",");
                String day=currentLineSplit[1];
                String month=currentLineSplit[2];
                String year=currentLineSplit[3];
                String name=currentLineSplit[0];
                String color=currentLineSplit[4];
                boolean isDiary;
                boolean isTodo;

                if (currentLineSplit[5].equals("true")){
                    isDiary=true;
                }
                else {
                    isDiary=false;
                }
                if (currentLineSplit[6].equals("true")){
                    isTodo=true;
                }
                else isTodo=false;

                rcordMarkers.add(new RecordMarker(name,day,month,year,color,isDiary,isTodo));
            }
        }
        catch (Exception e){

        }
    }


//初始化
    @FXML
    public void initialize(){
        readRcordMarkerCSV();
        changeTheme();
        markerInfoBar.setVisible(false);
        markerInfoBar.managedProperty().bind(markerInfoBar.visibleProperty());

        editRecord.setVisible(false);
        editRecord.managedProperty().bind(editRecord.visibleProperty());

        errorText.setVisible(false);
        errorEditText.setVisible(false);
    }

//    将周一到周天写入到网格里面去
    public void setWeekDays(){
    for (int i=0;i<daysArrayChinese.length;i++)
    {
        Label dayLabel=new Label(daysArrayChinese[i]);
        dayLabel.setTextFill(Color.web("#ffffff"));
        calenderGrid.add(dayLabel,i,0);
    }
}

//    将当前grippane所在的月份和年份写入到顶部的标签中去
    public  void  setMenuMonthYear(String month ,String year)
{
    monthLabel.setText(month);
    yearLabel.setText(year+"年");
}

//    在日历网格内载入数据
    public void setCalender(String day,int DayAsNum,String month,String year){
    markerInfoBar.setVisible(false);
    editRecord.setVisible(false);
    setWeekDays();
    setMenuMonthYear(MonthTranslation(month),year);

//        确定现在所在的是星期几
    int currentPositionInDaysList= 0;

//        通过传进来的星期值与星期数组做对比，确定现在所在的星期几，将星期数组的下标存到上面的int类型里面
    for (int i=0;i<daysArray.length;i++){
        if (daysArray[i].equals(day)){
            currentPositionInDaysList=i;
        }
    }

//        确定输入月份所在的第一天是星期几，从而在第一行的第几个格子开始生成数字
//        原理是：获得的是天数，最多只能判断最后一天的所在位置是星期几，要想知道第一天是星期几，可以天数-1的同时星期数组所确定的下标-1 。注意关键点在于当位置为-1时要修改为6
    while (DayAsNum!=1){
//            如果所咋的位置是星期一的上一位则把其设置在星期天的位置
        if (currentPositionInDaysList==-1){
            currentPositionInDaysList=6;
        }
        currentPositionInDaysList-=1;
        DayAsNum-=1;
    }
//        出循环只是意味着找到第一天的位置，但是有可能所在位置为-1，要先修改其为6
    if (currentPositionInDaysList==-1){
        currentPositionInDaysList=6;
    }
//        现在才真正确定了是这个月的第一天是星期几
    dayCMonthBegan=daysArray[currentPositionInDaysList];

    int rowPosition=1;
    int dayLimit=HowManyDaysInTheMonth.howManyDays(month,year);
//        由于传进来的天数给用了，所以这里再获得一下该年该月下的天数

//        循环遍历生成天数
    while (DayAsNum!=dayLimit+1){
//            生成完一周后，将下标拨回去到0，生成数字的行数加1
        if (currentPositionInDaysList>6){
            rowPosition +=1;
            currentPositionInDaysList=0;
        }

//            这里要给label和每个grid里面的vbox留id，方便后面增删改查记录

        Label l1=new Label(String.valueOf(DayAsNum));
        l1.setId("day"+DayAsNum);

//            这里留着为全局颜色所留位置
        l1.setTextFill(Color.web("#ffffff"));

        VBox vBox=new VBox();
        vBox.setId("grid"+DayAsNum);
        vBox.getStyleClass().add("Calenderpane");
        vBox.getChildren().add(l1);
        vBox.setOnMouseClicked(e -> {dayClicked(vBox,l1);});

        //        当你进行操作点击到上一页或者下一页的功能时，有可能有些格子是高亮着的，要把那些格子变回原样
        for (int i=0;i<rcordMarkers.size();i++)
        {
            if (DayAsNum==Integer.parseInt(rcordMarkers.get(i).getDay())&& month.equals(rcordMarkers.get(i).getMonth())){
                Label l2=new Label(rcordMarkers.get(i).getName());
                HBox h1=new HBox();
                h1.getStyleClass().add("dayGridStyle");
                String[] splitRcordDayColor=rcordMarkers.get(i).getColor().split("0x");
                h1.setStyle("-fx-background-color: #"+splitRcordDayColor[1]+";");
                h1.getChildren().add(l2);
                int j=i;
                h1.setOnMouseClicked(e ->{ markerClicked(h1,l2,rcordMarkers.get(j));});
                vBox.getChildren().add(h1);
            }
        }
        calenderGrid.add(vBox,currentPositionInDaysList,rowPosition);

        dayCMonthEnded=daysArray[currentPositionInDaysList];
        //        增加列的位置和日期
        currentPositionInDaysList +=1;
        DayAsNum+=1;
    }
}


//    当用户点击了，日历格子上的日记类型的记录发生的事情
    private void markerClicked(HBox h1,Label l2,RecordMarker r1){
    errorEditText.setVisible(false);

    currentRcordMarker=r1;
    editRecord.setVisible(true);
    markerEditNameField.setText(r1.getName());
    markerEditColorPicker.setValue(Color.valueOf(r1.getColor()));
    editRadioBdiary.setSelected(r1.isDiary());
    editRadioBDo.setSelected(r1.isTodo());

    if (editRadioBdiary.isSelected()){
        editRadioBDo.setSelected(false);
    }
    markerHasBeenClicked=true;
}


//    标记已经被点击的
    boolean markerHasBeenClicked=false;

//    日历格子被点击后发生的事情
    private void dayClicked(VBox v1, Label l1) {
//        首先得考虑的是，如果已经有格子被点了，用户点击第二个格子
//        创建记录部分的盒子将该复原的都复原了
    errorText.setVisible(false);
    markerNameField.setText("");
    markerColorPicker.setValue(Color.web("#FFFFFF"));
    addRadioBdiary.setSelected(false);
    addRadioBDo.setSelected(false);


//        日历格子中，将上一个被高亮的格子恢复成原本的样子
    if (!(currentDayNote==null))
    {
        currentDayNote.setStyle("-fx-background-color: transparent");
    }

//        将创建记录的板块展示出来
    markerInfoBar.setVisible(true);
    currentDayNote=v1;
    currentDayLabel=l1;

//        让新建记录和编辑记录两个板块不能同时出现
    if (markerHasBeenClicked){
        markerInfoBar.setVisible(false);
    }
    else{
        editRecord.setVisible(false);
    }
    markerHasBeenClicked=false;

//       日记类型和待做类型只能二选一
    addRadioBdiary.selectedProperty().addListener(e ->{
        if (addRadioBdiary.isSelected()){
            addRadioBDo.setSelected(false);
        }
        else{
            addRadioBDo.setSelected(true);
        }

//            选中日历格子后对这个格子变色
        currentDayNote.setStyle("-fx-background-color: #282e3b");

    });
    currentDayNote.setStyle("-fx-background-color: #39464b");
}

//    关闭节点发生的事情（让节点所在的盒子的背景颜色变回透明）
    public void closeNode(){
        markerInfoBar.setVisible(false);
        currentDayNote.setStyle("-fx-background-color: transparent");
    }

    @FXML
    public void closeEditNode(){
        editRecord.setVisible(false);
    }


    //   英文月份字与中文月份字转换
    public String MonthTranslation(String EnglishMonth){
        String ChineseMonth = null;
        switch (EnglishMonth){
            case ("Jan"): ChineseMonth="一月";break;
            case("Feb"):ChineseMonth="二月";break;
            case("Mar"):ChineseMonth="三月";break;
            case("Apr"):ChineseMonth="四月";break;
            case("May"):ChineseMonth="五月";break;
            case("Jun"):ChineseMonth="六月";break;
            case("Jul"):ChineseMonth="七月";break;
            case ("Aug"):ChineseMonth="八月";break;
            case ("Sep"):ChineseMonth="九月";break;
            case ("Oct"):ChineseMonth="十月";break;
            case ("Nov"):ChineseMonth="十一月";break;
            case ("Dec"):ChineseMonth="十二月";break;
        }
        return ChineseMonth;
    }
    public String DayTranslation(String EnglishDay){
        String ChineseDay= null;
        switch (EnglishDay){
            case ("Mon"):ChineseDay="周一";break;
            case ("Tue"):ChineseDay="周二";break;
            case ("Wed"):ChineseDay="周三";break;
            case ("Thu"):ChineseDay="周四";break;
            case ("Fri"):ChineseDay="周五";break;
            case ("Sat"):ChineseDay="周六";break;
            case ("Sun"):ChineseDay="周天";break;
        }
        return ChineseDay;
    }

    public  void changeTheme(){
        String PriBackgroundColor= "-fx-background-color: #121212;";
        String SecBackgroundColor= "-fx-background-color: #282828;";
        String TerBackgroundColor= "-fx-background-color: #BB86FC;";

        String PriTextColor= "#FEFEFE";
        String SecTextColor = "#000000";

        vContainer.setStyle(PriBackgroundColor);

        monthLabel.setTextFill(Color.web(PriTextColor));
        yearLabel.setTextFill(Color.web(PriTextColor));
        nameLabel.setTextFill(Color.web(PriTextColor));
        nameEditLabel.setTextFill(Color.web(PriTextColor));
        colorLabel.setTextFill(Color.web(PriTextColor));
        colorEditLabel.setTextFill(Color.web(PriTextColor));

        userFinishButton.setStyle(TerBackgroundColor);
        userEditButton.setStyle(TerBackgroundColor);
        userDeleteButton.setStyle(TerBackgroundColor);

        addRadioBdiary.setTextFill(Color.web(PriTextColor));
        addRadioBDo.setTextFill(Color.web(PriTextColor));
        editRadioBdiary.setTextFill(Color.web(PriTextColor));
        editRadioBDo.setTextFill(Color.web(PriTextColor));

        markerNameField.setStyle(TerBackgroundColor);
        markerEditNameField.setStyle(TerBackgroundColor);

        markerColorPicker.setStyle(TerBackgroundColor);
        markerEditColorPicker.setStyle(TerBackgroundColor);

        markerInfoBar.setStyle(SecBackgroundColor);
        editRecord.setStyle(SecBackgroundColor);

        titleBar.setStyle(SecBackgroundColor);
        menuHbox.setStyle(SecBackgroundColor);

        markerInfoClose.setTextFill(Color.web(PriTextColor));
        markerEditInfoClose.setTextFill(Color.web(PriTextColor));

        calenderGrid.getChildren().clear();
        setCalender(cDay,cDayAsNum,cMonth,cYear);
    }
}
