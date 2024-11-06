package ru.cft.shiftlab.contentmaker.util.keycloak;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KeyCloak {
    @AllArgsConstructor
    public enum Roles{
        ADMIN ("ROLE_ADMIN"),
        USER ("ROLE_USER");
        private final String title;
        public static Roles fromString(String role){
            return Arrays.stream(Roles.values())
                    .filter(x->x.title.equals(role))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unexpected role"));
        }
    }
    public Set<Roles> getRoles(){
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(x -> x.startsWith("ROLE_"))
                .map(Roles::fromString)
                .collect(Collectors.toUnmodifiableSet());
    }
    public String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
