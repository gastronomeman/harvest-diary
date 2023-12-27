package com.HarvestDiary.otherTools;

public interface HowManyDaysInTheMonth {

//    会返回输入的月份的天数，输入所在的月份和年份
//    年份用来确定是闰年还是平年
    static int howManyDays(String mouth,String year){
        int Days=0;
        switch (mouth){
            case ("Jan"), ("Mar"),("May"),("Jul"),("Aug"),("Oct"),("Dec"):{ Days=31;
                break;
            }
            case ("Feb"):{
                int yearValue=Integer.parseInt(year);
                boolean leap=false;

                if (yearValue%4==0){
                    if (yearValue%100==0){
                        if (yearValue%400==0){
                            leap=true;
                        }else
                            leap=false;
                    }else
                        leap=true;
                }else
                    leap=false;

                if (!leap){
                    Days=28;
                }
                else {
                    Days=29;
                }
                break;
            }
            case ("Apr"),("Jun"),("Sep"),("Nov"):{
                Days=30;
                break;
            }
        }
        return Days;
    }
}
