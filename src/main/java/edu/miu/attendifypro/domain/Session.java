package edu.miu.attendifypro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author kush
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}
