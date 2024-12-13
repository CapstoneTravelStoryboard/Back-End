package com.example.travel_sculptor.dto.fastapi;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class IotroFastapiResponseDTO {
    private List<String> intros;
    private List<String> outros;
}
