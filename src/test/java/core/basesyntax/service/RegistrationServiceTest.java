package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ExpectedException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        validUser = new User();
        validUser.setPassword("Password123");
        validUser.setLogin("ValidUser");
        validUser.setAge(18);
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(ExpectedException.class, () -> registrationService.register(null));
    }

    @Test
    void register_passwordNull_notOk() {
        User userWithNullPassword = validUser;
        userWithNullPassword.setPassword(null);
        assertThrows(ExpectedException.class, () ->
                registrationService.register(userWithNullPassword));
    }

    @Test
    void register_passwordEmpty_notOk() {
        User userWithEmptyPassword = validUser;
        userWithEmptyPassword.setPassword("");
        assertThrows(ExpectedException.class, () ->
                registrationService.register(userWithEmptyPassword));
    }

    @Test
    void register_passwordWhiteSpaced_notOk() {
        User userWithEmptyPassword = validUser;
        userWithEmptyPassword.setPassword("                 ");
        assertThrows(ExpectedException.class, () ->
                registrationService.register(userWithEmptyPassword));
    }

    @Test
    void register_passwordLessThanRequired_notOk() {
        User userWithEmptyPassword = validUser;
        userWithEmptyPassword.setPassword("1234");
        assertThrows(ExpectedException.class, () ->
                registrationService.register(userWithEmptyPassword));
    }

    @Test
    void register_loginNull_notOk() {
        User userWithNullLogin = validUser;
        userWithNullLogin.setLogin(null);
        assertThrows(ExpectedException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void register_loginEmpty_notOk() {
        User userWithEmptyLogin = validUser;
        userWithEmptyLogin.setLogin("");
        assertThrows(ExpectedException.class, () ->
                registrationService.register(userWithEmptyLogin));
    }

    @Test
    void register_loginWhiteSpaced_notOk() {
        User userWithEmptyLogin = validUser;
        userWithEmptyLogin.setLogin("                 ");
        assertThrows(ExpectedException.class, () ->
                registrationService.register(userWithEmptyLogin));
    }

    @Test
    void register_loginLessThanRequired_notOk() {
        User userWithEmptyLogin = validUser;
        userWithEmptyLogin.setLogin("log");
        assertThrows(ExpectedException.class, () ->
                registrationService.register(userWithEmptyLogin));
    }

    @Test
    void register_nullAgedUser_notOk() {
        User nullAgedUser = validUser;
        nullAgedUser.setAge(null);
        assertThrows(ExpectedException.class, () ->
                registrationService.register(nullAgedUser));
    }

    @Test
    void register_underAgedUser_notOK() {
        User nullAgedUser = validUser;
        nullAgedUser.setAge(17);
        assertThrows(ExpectedException.class, () ->
                registrationService.register(nullAgedUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User negativeAgeUser = validUser;
        negativeAgeUser.setAge(-1);
        assertThrows(ExpectedException.class, () ->
                registrationService.register(negativeAgeUser));
    }

    @Test
    void register_validUser_isOk() {
        assertThrows(ExpectedException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void register_equalUsers_notOK() {
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("userForEquals");
        firstUser.setPassword("password");
        User equalUser = new User();
        equalUser.setAge(18);
        equalUser.setLogin("userForEquals");
        equalUser.setPassword("password");
        try {
            registrationService.register(firstUser);
            registrationService.register(equalUser);
            assertEquals(1, Storage.people.size());
        } catch (ExpectedException e) {
            throw new RuntimeException("Cannot register such a User ", e);
        }
    }
}
