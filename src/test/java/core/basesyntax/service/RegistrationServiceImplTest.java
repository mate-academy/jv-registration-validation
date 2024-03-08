package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.userinvalidexeption.UserInvalidExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
    }

    @Test
    void register_ValidUser_Ok() {
        User validUser = new User();
        validUser.setLogin("john_doe");
        validUser.setPassword("strongPassword");
        validUser.setAge(25);

        User registeredUser = registrationService.register(validUser);

        assertNotNull(registeredUser.getId());
        assertEquals(validUser.getLogin(), registeredUser.getLogin());
        assertEquals(validUser.getPassword(), registeredUser.getPassword());
        assertEquals(validUser.getAge(), registeredUser.getAge());
    }

    @Test
    void register_UserWithShortLogin_Not_Ok() {
        User userWithShortLogin = new User();
        userWithShortLogin.setLogin("short");
        userWithShortLogin.setPassword("validPassword");
        userWithShortLogin.setAge(30);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(userWithShortLogin));
    }

    @Test
    void register_UserWithExistingLogin_Not_ok() {
        User existingUser = new User();
        existingUser.setLogin("existing_user");
        existingUser.setPassword("validPassword");
        existingUser.setAge(28);

        registrationService.register(existingUser);

        User userWithExistingLogin = new User();
        userWithExistingLogin.setLogin("existing_user");
        userWithExistingLogin.setPassword("anotherValidPassword");
        userWithExistingLogin.setAge(32);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(userWithExistingLogin));
    }

    @Test
    void register_UserWithShortPassword_Noy_Ok() {
        User userWithShortPassword = new User();
        userWithShortPassword.setLogin("valid_user");
        userWithShortPassword.setPassword("short");
        userWithShortPassword.setAge(22);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(userWithShortPassword));
    }

    @Test
    void register_UserWithEmptyPassword_Not_Ok() {
        User userWithEmptyPassword = new User();
        userWithEmptyPassword.setLogin("valid_user");
        userWithEmptyPassword.setPassword("");
        userWithEmptyPassword.setAge(26);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(userWithEmptyPassword));
    }

    @Test
    void register_UnderageUser_Noy_Ok() {
        User underageUser = new User();
        underageUser.setLogin("young_user");
        underageUser.setPassword("validPassword");
        underageUser.setAge(17);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(underageUser));
    }
}
