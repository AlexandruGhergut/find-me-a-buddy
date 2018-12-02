package com.fmi.findmeabuddy.domain.tracing;

import java.time.ZonedDateTime;

public interface DateTimeTrackableEntity {
    
    ZonedDateTime getCreatedAt();
    
    void setCreatedAt(ZonedDateTime createdAt);
    
    ZonedDateTime getUpdatedAt();
    
    void setUpdatedAt(ZonedDateTime updatedAt);
}
