package com.HarvestDiary.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
    private String userId;
    private LocalDate time;
    private String color;
    private String fontSize;
    private String title;
    private String content;

}
