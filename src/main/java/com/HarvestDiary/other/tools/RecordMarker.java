package com.HarvestDiary.other.tools;


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
//        if (diary){
//
//        }
        try {
            FileWriter fileWriter=new FileWriter("keeper.csv",true);
            fileWriter.write(name+","+day+","+month+","+year+","+color+","+diary+","+todo+"\n");
            fileWriter.close();
        }catch (IOException e)
        {
            throw new RuntimeException();
        }
    }

    private void aaa(){
        String[] monthsArray={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        Date cDate=new Date();
        String[] cDateSplit =cDate.toString().split(" ");
        String cDay=cDateSplit[2];
        String cMonth=cDateSplit[1];
        String cYear=cDateSplit[5];
    }
}
