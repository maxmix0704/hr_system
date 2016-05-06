package ua.netcracker.model.utils;

import org.springframework.security.core.GrantedAuthority;
import ua.netcracker.model.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to work with roles (authority granted)
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public class RolesUtils {

    private static boolean isAdmin(List<String> roles) {
        return roles.contains(Role.ADMIN.toString());
    }

    private static boolean isHR(List<String> roles) {
        return roles.contains(Role.HR.toString());
    }

    private static boolean isBA(List<String> roles) {
        return roles.contains(Role.BA.toString());
    }

    private static boolean isDev(List<String> roles) {
        return roles.contains(Role.DEV.toString());
    }

    private static boolean isStudent(List<String> roles) {
        return roles.contains(Role.STUDENT.toString());
    }

    public static String getUrlByRoles(Collection<? extends GrantedAuthority> authorities) {
        String url;

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isAdmin(roles)) {
            url = "/admin";
        } else if (isHR(roles)) {
            url = "/hr";
        } else if (isBA(roles)) {
            url = "/ba";
        } else if (isDev(roles)) {
            url = "/dev";
        } else if (isStudent(roles)) {
            url = "/student";
        } else {
            url = "/error";
        }
        return url;
    }

    public static Role getRoleByStr(String role) {
        if (Role.ADMIN.toString().equals(role))
            return Role.ADMIN;
        if (Role.STUDENT.toString().equals(role))
            return Role.STUDENT;
        if (Role.HR.toString().equals(role))
            return Role.HR;
        if (Role.BA.toString().equals(role))
            return Role.BA;
        if (Role.DEV.toString().equals(role))
            return Role.DEV;
        return null;
    }
}
