package edu.miu.attendifypro.dto.response;

import edu.miu.attendifypro.domain.LocationType;
import jakarta.persistence.*;

public class LocationResponse {
    private long id;
    private int capacity;
    private String name;
    private LocationType locationType;
}
