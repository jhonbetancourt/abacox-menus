package com.infomedia.abacox.menus.component.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.infomedia.abacox.menus.config.JsonConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ResponseMessage extends WSMessage {
    private UUID id;
    private String source;
    private Instant timestamp;
    private boolean success;
    private String exception;
    private String errorMessage;
    private JsonNode result;

    public <T> T getResult(Class<T> clazz) {
        return JsonConfig.getObjectMapper().convertValue(result, clazz);
    }
}
