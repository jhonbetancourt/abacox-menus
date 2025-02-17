package com.infomedia.abacox.menus.dto.menushortcut;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwapMenuShortcut {
    @NotBlank
    @Schema(description = "Username of the user who has the shortcut", example = "admin")
    private String username;

    @NotNull
    @Min(0)
    @Max(99)
    @Schema(description = "Position of the first shortcut", example = "0")
    private Integer position1;

    @NotNull
    @Min(0)
    @Max(99)
    @Schema(description = "Position of the second shortcut", example = "1")
    private Integer position2;
}