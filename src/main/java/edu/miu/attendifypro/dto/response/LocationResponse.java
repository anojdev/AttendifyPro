package edu.miu.attendifypro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private long id;
    private int capacity;
    private String name;
    private LocationTypeDto locationType;
}
