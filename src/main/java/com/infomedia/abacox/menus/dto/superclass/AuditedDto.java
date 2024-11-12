package com.infomedia.abacox.menus.dto.superclass;

import com.infomedia.abacox.menus.constants.DateTimePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class AuditedDto {
    @JsonFormat(pattern = DateTimePattern.DATE_TIME)
    @Schema(description = "created date", example = "2021-08-01T00:00:00")
    LocalDateTime createdDate;
    @Schema(description = "created by", example = "admin")
    String createdBy;
    @JsonFormat(pattern = DateTimePattern.DATE_TIME)
    @Schema(description = "last modified date", example = "2021-08-01T00:00:00")
    LocalDateTime lastModifiedDate;
    @Schema(description = "last modified by", example = "admin")
    String lastModifiedBy;
}