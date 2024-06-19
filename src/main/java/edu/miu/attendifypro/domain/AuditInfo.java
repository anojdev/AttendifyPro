package edu.miu.attendifypro.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author kush
 */
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditInfo {

    @CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;
    @LastModifiedDate
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
//    private String createdBy;
//    private String updatedBy;

    @CreatedBy
    String createdBy;

    @LastModifiedBy
    String updatedBy;
}