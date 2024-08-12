package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String LOGIN_WITH_MIN_LENGTH = "io0p12";
    private static final String PASSWORD_WITH_MIN_LENGTH = "123456";
    private static final String VALID_LOGIN = "lqwerty123";
    private static final String VALID_PASSWORD = "qwe123qwe";
    private static final int VALID_AGE = 23;

    @Test
    public void register_Null_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_NullLogin_NotOk() {
        User user = getValidUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullPassword_NotOk() {
        User user = getValidUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullAge_NotOk() {
        User user = getValidUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_AddLogin_NotOk() {
        User user = getValidUser();
        StringBuilder login = new StringBuilder();
        user.setLogin(login.toString());

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        for (int i = 1; i < MIN_LOGIN_LENGTH; i++) {
            user.setLogin(login.append(i).toString());
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    public void register_AlreadyExistedUser_NotOk() {
        User user = getValidUser();
        Storage.people.add(user);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        Storage.people.remove(user);
    }

    @Test
    public void register_AddPassword_NotOk() {
        User user = getValidUser();
        StringBuilder password = new StringBuilder();
        user.setPassword(password.toString());

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        for (int i = 1; i < MIN_PASSWORD_LENGTH; i++) {
            user.setPassword(password.append(i).toString());
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    public void register_AddAge_NotOk() {
        User user = getValidUser();

        for (int i = 0; i < MIN_AGE; i++) {
            user.setAge(i);
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    public void register_AddValidUser_Ok() {
        User user = getValidUser();
        assertEquals(registrationService.register(user), user);

        for (int i = 0; i <= 100; i++) {
            User newUser = new User();
            newUser.setLogin(LOGIN_WITH_MIN_LENGTH + i);
            newUser.setPassword(PASSWORD_WITH_MIN_LENGTH + i);
            newUser.setAge(MIN_AGE + i);
            assertEquals(registrationService.register(newUser), newUser);
        }
    }

    @Test
    public void register_AddMinimumValidValues_Ok() {
        User user = new User();
        user.setLogin(LOGIN_WITH_MIN_LENGTH);
        user.setPassword(PASSWORD_WITH_MIN_LENGTH);
        user.setAge(MIN_AGE);
        assertEquals(registrationService.register(user), user);
    }

    private User getValidUser() {
        User validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
        return validUser;
    }
}
