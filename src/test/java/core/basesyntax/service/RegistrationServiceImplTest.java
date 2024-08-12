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
    public void register_NullUser_NotOk() {
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
    public void register_InvalidLogin_NotOk() {
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
    public void register_AddInvalidPassword_NotOk() {
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
    public void register_AddInvalidAge_NotOk() {
        User user = getValidUser();

        for (int i = 0; i < MIN_AGE; i++) {
            user.setAge(i);
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
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
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        return user;
    }
}
