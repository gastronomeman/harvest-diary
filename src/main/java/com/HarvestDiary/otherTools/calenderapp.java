package com.HarvestDiary.otherTools;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class calenderapp {
   /* @Override
    public void start(Stage stage) throws Exception {
        CalendarView calendarView=new CalendarView();

        Calendar birthdays=new Calendar("birthdays");

        CalendarSource calendarSource=new CalendarSource("my Calendars");

        calendarSource.getCalendars().addAll(birthdays);

        calendarView.getCalendarSources().add(calendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread =new Thread("Calendar:update Time Thread"){
            @Override
            public void run() {
                while (true){
                    Platform.runLater(()->{
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });
                    try {
//                        每十秒1刷新
                        sleep(1000);

                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);

        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        Scene scene=new Scene(calendarView);

        stage.setTitle("更吊的日历类");
        stage.setScene(scene);
        stage.setWidth(1300);
        stage.setHeight(1000);

        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }*/
}
