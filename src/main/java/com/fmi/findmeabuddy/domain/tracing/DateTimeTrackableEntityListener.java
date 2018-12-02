package com.fmi.findmeabuddy.domain.tracing;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeTrackableEntityListener {
    @PrePersist
    public void createdAt(DateTimeTrackableEntity entity) {
        ZonedDateTime currentDate = ZonedDateTime.now(ZoneOffset.UTC);
        entity.setCreatedAt(currentDate);
        entity.setUpdatedAt(currentDate);
    }
    
    @PreUpdate
    public void updatedAt(DateTimeTrackableEntity entity) {
        entity.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
    }
}
