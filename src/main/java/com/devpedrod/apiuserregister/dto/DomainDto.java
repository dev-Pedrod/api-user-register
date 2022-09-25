package com.devpedrod.apiuserregister.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public abstract class DomainDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime disabledAt;
}
