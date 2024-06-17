package edu.miu.attendifypro.dto.request;

import edu.miu.attendifypro.domain.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationUpdateRequest {
    @Size(min = 1, message = "{validation.name.size.exceed}")
    private int capacity;
    @NotBlank(message = "{should.not.be.empty}")
    @NotNull(message = "{required}")
    @Size(max = 15, message = "{validation.name.size.exceed}")
    private String name;
    private LocationType locationType;
}
