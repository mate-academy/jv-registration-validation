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

    private String getPassWithMinLength(int passLength) {
        StringBuilder pass = new StringBuilder(passLength);
        for (int i = 0; i < passLength; i++) {
            pass.append(i);
        }
        return pass.toString();
    }

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
        User actualFirstUserReturn = registrationService.register(firstUser);
        User actualSecondUserReturn = registrationService.register(secondUser);
        int actual = Storage.people.size();
        assertEquals(2, actual);
        assertEquals(firstUser, actualFirstUserReturn);
        assertEquals(secondUser, actualSecondUserReturn);
    }

    @Test
    void register_userMinAge_Ok() {
        firstUser.setAge(RegistrationServiceImpl.MIN_USER_AGE);
        secondUser.setAge(RegistrationServiceImpl.MIN_USER_AGE);
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }

    @Test
    void register_userMinPasswordLength_Ok() {
        firstUser.setPassword(getPassWithMinLength(RegistrationServiceImpl
                .MIN_PASSWORD_LENGTH));
        secondUser.setPassword(getPassWithMinLength(RegistrationServiceImpl
                .MIN_PASSWORD_LENGTH));
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }

    @Test
    void register_loginExist_NotOk() {
        registrationService.register(firstUser);
        secondUser.setLogin(firstUser.getLogin());
        assertThrows(ValidationException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_lessThanMinAge_NotOk() {
        firstUser.setAge(RegistrationServiceImpl.MIN_USER_AGE - 1);
        assertThrows(ValidationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_passwordLessThanMinLength_NotOk() {
        firstUser.setPassword(getPassWithMinLength(RegistrationServiceImpl
                .MIN_PASSWORD_LENGTH - 1));
        assertThrows(ValidationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_UserIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_NullLogin_NotOk() {
        firstUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_NullAge_NotOk() {
        firstUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_NullPassword_NotOk() {
        firstUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }
}
