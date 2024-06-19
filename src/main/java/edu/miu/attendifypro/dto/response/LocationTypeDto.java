package edu.miu.attendifypro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author kush
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeDto {
    private long id;

    private String type;
}
