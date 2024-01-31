package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.EmptyLoginException;
import core.basesyntax.exceptions.EmptyPasswordException;
import core.basesyntax.exceptions.EqualUsersException;
import core.basesyntax.exceptions.LengthLoginException;
import core.basesyntax.exceptions.LengthPasswordException;
import core.basesyntax.exceptions.NegativeAgeException;
import core.basesyntax.exceptions.NullAgeException;
import core.basesyntax.exceptions.NullLoginException;
import core.basesyntax.exceptions.NullPasswordException;
import core.basesyntax.exceptions.NullUserException;
import core.basesyntax.exceptions.UnderAgeException;
import core.basesyntax.exceptions.WhiteSpacedPasswordException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User validUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        validUser = new User();
        validUser.setPassword("Password123");
        validUser.setLogin("ValidUser");
        validUser.setAge(18);
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(NullUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_passwordNull_notOk() {
        User userWithNullPassword = validUser;
        userWithNullPassword.setPassword(null);
        assertThrows(NullPasswordException.class, () ->
                registrationService.register(userWithNullPassword));
    }

    @Test
    void register_passwordEmpty_notOk() {
        User userWithEmptyPassword = validUser;
        userWithEmptyPassword.setPassword("");
        assertThrows(EmptyPasswordException.class, () ->
                registrationService.register(userWithEmptyPassword));
    }

    @Test
    void register_passwordWhiteSpaced_notOk() {
        User userWithEmptyPassword = validUser;
        userWithEmptyPassword.setPassword("                 ");
        assertThrows(WhiteSpacedPasswordException.class, () ->
                registrationService.register(userWithEmptyPassword));
    }

    @Test
    void register_passwordLessThanRequired_notOk() {
        User userWithEmptyPassword = validUser;
        userWithEmptyPassword.setPassword("1234");
        assertThrows(LengthPasswordException.class, () ->
                registrationService.register(userWithEmptyPassword));
    }

    @Test
    void register_loginNull_notOk() {
        User userWithNullLogin = validUser;
        userWithNullLogin.setLogin(null);
        assertThrows(NullLoginException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void register_loginEmpty_notOk() {
        User userWithEmptyLogin = validUser;
        userWithEmptyLogin.setLogin("");
        assertThrows(EmptyLoginException.class, () ->
                registrationService.register(userWithEmptyLogin));
    }

    @Test
    void register_loginWhiteSpaced_notOk() {
        User userWithEmptyLogin = validUser;
        userWithEmptyLogin.setLogin("                 ");
        assertThrows(EmptyLoginException.class, () ->
                registrationService.register(userWithEmptyLogin));
    }

    @Test
    void register_loginLessThanRequired_notOk() {
        User userWithEmptyLogin = validUser;
        userWithEmptyLogin.setLogin("log");
        assertThrows(LengthLoginException.class, () ->
                registrationService.register(userWithEmptyLogin));
    }

    @Test
    void register_nullAgedUser_notOk() {
        User nullAgedUser = validUser;
        nullAgedUser.setAge(null);
        assertThrows(NullAgeException.class, () ->
                registrationService.register(nullAgedUser));
    }

    @Test
    void register_underAgedUser_notOK() {
        User nullAgedUser = validUser;
        nullAgedUser.setAge(17);
        assertThrows(UnderAgeException.class, () ->
                registrationService.register(nullAgedUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User negativeAgeUser = validUser;
        negativeAgeUser.setAge(-1);
        assertThrows(NegativeAgeException.class, () ->
                registrationService.register(negativeAgeUser));
    }

    @Test
    void register_validUser_isOk() {
        int sizeAfterAddingUser = Storage.people.size() + 1;
        registrationService.register(validUser);
        boolean containsValidUser = Storage.people.contains(validUser);
        assertTrue(containsValidUser);
        assertEquals(sizeAfterAddingUser, Storage.people.size());
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
        registrationService.register(firstUser);
        assertThrows(EqualUsersException.class, () -> registrationService.register(equalUser));
    }
}
