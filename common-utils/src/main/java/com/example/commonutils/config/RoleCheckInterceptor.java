package com.example.commonutils.config;

import com.example.commonutils.JsonUtils;
import com.example.commonutils.annotation.Roles;
import com.example.commonutils.dto.AuthConst;
import com.example.commonutils.dto.IResponse;
import com.example.commonutils.dto.UserRoleInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;


public class RoleCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (handler instanceof HandlerMethod handlerMethod) {
            Roles roleCheck = handlerMethod.getMethodAnnotation(Roles.class);
            if (roleCheck != null) {
                // 获取用户角色
                UserRoleInfo userRole = getUserRole(request);
                // 检查用户角色是否在注解指定的角色列表中
                if (roleCheck.value().length > 0 && userRole == null) {
                    IResponse<String> iResponse = IResponse.error(IResponse.ERR_AUTHORITY, "authority error");
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(iResponse);
                    response.getWriter().write(json);
                    return false;
                }
                if (roleCheck.value().length > 0 && !new HashSet<>(userRole.getRoles()).containsAll(List.of(roleCheck.value()))) {
                    // 如果用户角色不在注解指定的角色列表中，拒绝访问
                    IResponse<String> iResponse = IResponse.error(IResponse.ERR_AUTHORITY, "authority error");
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(iResponse);
                    response.getWriter().write(json);
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private UserRoleInfo getUserRole(HttpServletRequest request) {
        String roleStr = request.getHeader(AuthConst.HEADER_AUTH);
        if (roleStr != null) {
            return JsonUtils.fromJson(roleStr, UserRoleInfo.class);
        }
        return null;
    }
}
