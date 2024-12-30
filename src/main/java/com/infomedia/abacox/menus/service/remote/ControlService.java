package com.infomedia.abacox.menus.service.remote;

import com.infomedia.abacox.menus.component.events.EventsWebSocketServer;
import com.infomedia.abacox.menus.dto.module.ModuleInfo;
import com.infomedia.abacox.menus.exception.RemoteServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class ControlService {

    private final EventsWebSocketServer eventsWebSocketServer;

    public ModuleInfo getInfoByPrefix(String prefix) {
        try {
            return eventsWebSocketServer.sendCommandRequestAndAwaitResponse("getModuleInfoByPrefix"
                    , Map.of("prefix", prefix)).getResultAs(ModuleInfo.class);
        } catch (IOException | TimeoutException e) {
            throw new RemoteServiceException("Error getting module info by prefix", e);
        }
    }
}
