package com.infomedia.abacox.menus.service.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.infomedia.abacox.menus.dto.generic.PageDto;
import com.infomedia.abacox.menus.dto.role.RoleDto;
import com.infomedia.abacox.menus.dto.user.UserContactInfoDto;
import com.infomedia.abacox.menus.dto.user.UserDto;
import com.infomedia.abacox.menus.service.AuthService;
import com.infomedia.abacox.menus.service.common.RemoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService extends RemoteService {

    private final ControlService controlService;

    protected UserService(AuthService authService, ControlService controlService) {
        super(authService);
        this.controlService = controlService;
    }

    public List<UserContactInfoDto> getContactInfoByRoles(List<String> roles) {
        TypeReference<List<UserContactInfoDto>> typeReference = new TypeReference<>() {};
        return get(Map.of("roles", roles), "/api/ext/user/contactInfoByRoles", typeReference);
    }

    public List<UserContactInfoDto> getContactInfoByUsernames(List<String> usernames) {
        TypeReference<List<UserContactInfoDto>> typeReference = new TypeReference<>() {};
        return get(Map.of("usernames", usernames), "/api/ext/user/contactInfoByUsernames", typeReference);
    }

    public PageDto<UserDto> findUsers(String filter, int page, int size, String sort) {
        Map<String, Object> params = Map.of("filter", filter==null?"":filter
                , "page", page, "size", size, "sort", sort==null?"":sort);
        TypeReference<PageDto<UserDto>> typeReference = new TypeReference<>() {};
        return get(params, "/api/user", typeReference);
    }

    public UserDto findUser(String filter) {
        PageDto<UserDto> users = findUsers(filter, 0, 1, null);
        return users.getContent().isEmpty() ? null : users.getContent().getFirst();
    }

    public PageDto<RoleDto> findRoles(String filter, int page, int size, String sort) {
        Map<String, Object> params = Map.of("filter", filter==null?"":filter
                , "page", page, "size", size, "sort", sort==null?"":sort);
        TypeReference<PageDto<RoleDto>> typeReference = new TypeReference<>() {};
        return get(params, "/api/role", typeReference);
    }

    public RoleDto findRole(String filter) {
        PageDto<RoleDto> roles = findRoles(filter, 0, 1, null);
        return roles.getContent().isEmpty() ? null : roles.getContent().getFirst();
    }

    @Override
    public String getBaseUrl() {
        return controlService.getInfoByPrefix("users").getUrl();
    }
}
