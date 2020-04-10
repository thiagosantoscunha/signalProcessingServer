package com.aimbra.paralellus.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data @NoArgsConstructor
public class DataResponse {
    private LocalTime startTime;
    private LocalTime endTime;
    private int duration;
    private String imageBase64;

    public DataResponse(LocalTime startTime, LocalTime endTime, int duration, String imageBase64) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.imageBase64 = imageBase64;
    }
}
