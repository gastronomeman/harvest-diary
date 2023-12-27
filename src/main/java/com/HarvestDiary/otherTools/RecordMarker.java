package com.HarvestDiary.otherTools;


import java.io.FileWriter;
import java.io.IOException;

import static com.HarvestDiary.otherTools.HowManyDaysInTheMonth.howManyDays;
public class RecordMarker {
    String name;
    String day;
    String month;
    String year;
    String color;
    boolean diary;
    boolean todo;

    public RecordMarker(String name, String day, String month, String year, String color, boolean diary, boolean todo) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.color = color;
        this.diary = diary;
        this.todo = todo;
    }

    public void addRecordToFile(){
        try {
            FileWriter fileWriter=new FileWriter("keeper.csv",true);
            fileWriter.write(name+","+day+","+month+","+year+","+color+","+diary+","+todo+"\n");
            fileWriter.close();
        }catch (IOException e)
        {
            throw new RuntimeException();
        }
    }
}
