package ru.cft.shiftlab.contentmaker.service.implementation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.UserDto;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


@Service
@RequiredArgsConstructor
@Getter
@Setter
@Log4j2
public class KeycloakServiceImpl {
    private final Keycloak keycloak;

    public RealmResource getRealm(){
        return keycloak.realm("content-maker");
    }
    public void createUser(UserDto user) throws Exception {
        UserRepresentation userRepresentation = new UserRepresentation();
        Response response;
        if (getRealm() == null){
            throw new StaticContentException("realm doesnt exist", "409");
        }
        if (!getRealm().users().search(user.getFirstName()).isEmpty()){
            throw new StaticContentException("user with this username is already exist", "409");
        }
        userRepresentation.setUsername(user.getFirstName());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(user.getEmail());
        try {
            response = getRealm().users().create(userRepresentation);
        }
        catch (WebApplicationException e){
            throw new StaticContentException(e.getMessage(), "409");
        }

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        String userId = CreatedResponseUtil.getCreatedId(response);
        passwordCred.setTemporary(false);
        passwordCred.setType("password");
        passwordCred.setValue(user.getPassword());
        UserResource userResource = getRealm().users().get(userId);
        userResource.resetPassword(passwordCred);
    }
    public void deleteUser(String userName) throws Exception{
        Response response;
        try {
            response = getRealm().users().delete(getRealm().users().search(userName).get(0).getId());
        }
        catch (Exception e){
            throw e;
        }

    }
}
