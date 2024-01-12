package com.harvestdiary.other.demo;

import java.time.LocalDate;

public class test {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println(today.getDayOfWeek().getValue());
    }
}
