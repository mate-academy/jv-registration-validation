package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final int defaultAge = 18;
    private final String defaultLogin = "login";
    private final String defaultPass = "password";
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User defaultUser;

    @BeforeEach
    void beforeEach() {
        defaultUser = new User();
        defaultUser.setAge(defaultAge);
        defaultUser.setLogin(defaultLogin);
        defaultUser.setPassword(defaultPass);
    }

    @Test
    void register_oneHundredUsers_ok() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setAge(defaultAge);
            user.setPassword(defaultPass);
            user.setLogin(defaultLogin + i);
            registrationService.register(user);
        }
        for (int i = 0; i < 100; i++) {
            User expectedUser = new User();
            expectedUser.setAge(defaultAge);
            expectedUser.setPassword(defaultPass);
            expectedUser.setLogin(defaultLogin + i);
            assertEquals(expectedUser, people.get(i));
        }
    }

    @Test
    void register_correctUser_ok() {
        User actual = registrationService.register(defaultUser);
        assertEquals(defaultUser, actual);
    }

    @Test
    void register_registerWithTheSameLogins_notOk() {
        User actual = registrationService.register(defaultUser);
        assertEquals(defaultUser, actual);
        User copiedUser = defaultUser;
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(copiedUser));
    }

    @Test
    void register_userWithNullFields_notOk() {
        User nullUser = new User();
        assertThrows(NullPointerException.class, () ->
                registrationService.register(nullUser));
    }

    @Test
    void register_userYounger18_notOk() {
        defaultUser.setAge(14);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(defaultUser));
        defaultUser.setAge(0);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(defaultUser));
        defaultUser.setAge(-3);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_smallPassword_notOk() {
        defaultUser.setPassword("passw");
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(defaultUser));
    }

    @AfterEach
    void tearDown() {
        people.clear();
    }
}
