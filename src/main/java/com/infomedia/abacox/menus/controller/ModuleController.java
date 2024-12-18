package com.infomedia.abacox.menus.controller;

import com.infomedia.abacox.menus.component.functiontools.FunctionCall;
import com.infomedia.abacox.menus.component.functiontools.FunctionResult;
import com.infomedia.abacox.menus.dto.module.EventTypesInfo;
import com.infomedia.abacox.menus.dto.module.MEndpointInfo;
import com.infomedia.abacox.menus.dto.module.ModuleInfo;
import com.infomedia.abacox.menus.service.LocalFunctionService;
import com.infomedia.abacox.menus.service.ModuleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Module", description = "Module controller")
@RequestMapping("/api/module")
public class ModuleController {

    private final ModuleService moduleService;
    private final LocalFunctionService localFunctionService;

    @GetMapping("/endpoints")
    public List<MEndpointInfo> getEndpoints() {
        return moduleService.getEndpoints();
    }

    @GetMapping("/info")
    public ModuleInfo getInfo() {
        return moduleService.getInfo();
    }

    @GetMapping("/eventTypes")
    public EventTypesInfo getEventTypes() {
        return moduleService.getEventTypes();
    }

    @SecurityRequirements({
            @SecurityRequirement(name = "JWT_Token"),
            @SecurityRequirement(name = "Username")
    })
    @PostMapping("/function/call")
    public FunctionResult callFunction(@Valid @RequestBody FunctionCall fc) {
        return localFunctionService.callFunction(fc.getService(), fc.getFunction(), fc.getArguments());
    }
}
