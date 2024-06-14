package edu.miu.attendifypro.domain;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

/**
 * @author kush
 */
@Embeddable
public class AuditInfo {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String createdBy;
    private String updatedBy;
}
