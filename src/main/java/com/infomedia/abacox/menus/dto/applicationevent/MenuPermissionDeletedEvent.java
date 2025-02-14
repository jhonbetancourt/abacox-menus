package com.infomedia.abacox.menus.dto.applicationevent;

// Create an event class
public class MenuPermissionDeletedEvent {
    private final Long menuPermissionId;

    public MenuPermissionDeletedEvent(Long menuPermissionId) {
        this.menuPermissionId = menuPermissionId;
    }

    public Long getMenuPermissionId() {
        return menuPermissionId;
    }
}