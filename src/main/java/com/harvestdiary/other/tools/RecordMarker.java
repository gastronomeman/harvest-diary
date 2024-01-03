package com.harvestdiary.other.tools;


import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class RecordMarker {
    String name;
     String day;
    String month;
    String year;
    String color;
    boolean diary;
    boolean todo;
    DiaryMarker diaryName;

    public RecordMarker(String name, String day, String month, String year, String color, boolean diary, boolean todo) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.color = color;
        this.diary = diary;
        this.todo = todo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDiary() {
        return diary;
    }

    public void setDiary(boolean diary) {
        this.diary = diary;
    }

    public boolean isTodo() {
        return todo;
    }

    public void setTodo(boolean todo) {
        this.todo = todo;
    }

    public  void addRecordToFile(){
        try {
            FileWriter fileWriter=new FileWriter("HarvestDiary/record.csv",true);
            fileWriter.write(name+","+day+","+month+","+year+","+color+","+diary+","+todo+"\n");
            fileWriter.close();
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void CheckFolder (){
//        找到存放数据的位置
        String folderPath="HarvestDiary/diary";
        File folder=new File(folderPath);

//        如果目录下有日记记录，则对他进行解码，如果没有则跳出
        if (folder.exists() && folder.isDirectory()){
            File[] files=folder.listFiles();
            if (files !=null && files.length>0){
                for (int i=0;i< files.length;i++){
//                    String a= "\""+ files[i].getName();
                    String a=files[i].getName();
                    String DiaryName= OperationalDocument.readFile("\\diary\\" + a);
                    String[] SplitOrigin=DiaryName.split(",");
                    String timeOrigin=SplitOrigin[1];
                    String titleOrigin=SplitOrigin[4];

                    String[]titleSplit=titleOrigin.split(":");
                    String[] timesplit1=timeOrigin.split(":");

                    String titleFinal=titleSplit[1].substring(1,titleSplit[1].length()-1);
                    String timeSub=timesplit1[1].substring(1,timesplit1[1].length()-1);

                    String[] timeFinal=RecordMarker.decoding(timeSub.split("-"));
                    RecordMarker r1=new RecordMarker(titleFinal,timeFinal[2],timeFinal[1],timeFinal[0],"0xffffffff",true,false);

//                    将当前的日记内容创建后之后，进行一次判断，如果原有记录里面已经有这样一模一样的内容了，就不写入了
                    r1.addRecordToFile();
                }
            }
        }
    }





    public static String[] decoding(String[] timeFinal){
        String[] monthsArray={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        String year=timeFinal[0];
        String month=timeFinal[1];
        String day=timeFinal[2];
        String newday="";
        String newMonth="";
        if (day.startsWith("0")){
           newday=day.substring(1);
        }
        switch (month){
            case "01": newMonth=monthsArray[0]; break;
            case "02": newMonth=monthsArray[1];break;
            case "03": newMonth=monthsArray[2];break;
            case "04": newMonth=monthsArray[3];break;
            case "05": newMonth=monthsArray[4];break;
            case "06": newMonth=monthsArray[5];break;
            case "07": newMonth=monthsArray[6];break;
            case "08": newMonth=monthsArray[7];break;
            case "09": newMonth=monthsArray[8];break;
            case "10": newMonth=monthsArray[9];break;
            case "11": newMonth=monthsArray[10];break;
            case "12": newMonth=monthsArray[11];break;
        }
        String[] newTime={year,newMonth,newday};
        return newTime;
    }
}
