package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User firstUser = new User();
    private User secondUser = new User();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        firstUser.setLogin("First");
        firstUser.setAge(27);
        firstUser.setPassword("12345678");
        secondUser.setLogin("Second");
        secondUser.setAge(29);
        secondUser.setPassword("87654321");
    }

    @Test
    void register_validUserAdd_Ok() {
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }

    @Test
    void register_userAge18_Ok() {
        firstUser.setAge(18);
        secondUser.setAge(18);
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }

    @Test
    void register_userPassSixSymbols_Ok() {
        firstUser.setPassword("123456");
        secondUser.setPassword("654321");
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }

    @Test
    void register_loginExist_NotOk() {
        registrationService.register(firstUser);
        secondUser.setLogin("First");
        assertThrows(ValidationException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_userToYoung_NotOk() {
        firstUser.setAge(16);
        assertThrows(ValidationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_PasswordToShort_NotOk() {
        firstUser.setPassword("123");
        assertThrows(ValidationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_UserIsNull_NotOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_NullLogin_NotOk() {
        firstUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_NullAge_NotOk() {
        firstUser.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_NullPassword_NotOk() {
        firstUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(firstUser));
    }
}
