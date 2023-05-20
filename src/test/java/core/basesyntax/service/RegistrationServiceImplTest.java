package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("kitty95");
        user.setAge(MIN_AGE);
        user.setPassword("123456");
    }

    @Test
    void register_addValidUser_Ok() {
        int sizeBefore = Storage.people.size();
        Storage.people.add(user);
        int sizeAfter = Storage.people.size();
        assertTrue(Storage.people.contains(user));
        assertEquals(1, sizeAfter - sizeBefore);
    }

    @Test
    void register_addTwoValidUsersWithSameLogin_notOk() {
        User secondUser = new User();
        secondUser.setLogin(user.getLogin());
        secondUser.setAge(MIN_AGE + 15);
        secondUser.setPassword("00123456");
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLessThanMinLength_notOk() {
        user.setLogin("kitty");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLessThanMinLength_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageUnderMinAge_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
