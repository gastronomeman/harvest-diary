package com.harvestdiary.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherAS {
    private String time;
    private String address;
    private String weather;
    private String wind;
}
