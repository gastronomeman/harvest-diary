package com.harvestdiary.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
    private String userId;
    private String time;
    private String color;
    private String fontSize;
    private String title;
    private String content;
}
