package com.example.travel_sculptor.dto.trip;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TripListRequestDTO {
    private String title;
    private LocalDate tripDate;
    private String locations;  //여행지 이름을 구분자를 추가하여 하나의 String으로 만들 것이다. ex) "서울특별시, 제주시, 서귀포시, 여수시"
}
