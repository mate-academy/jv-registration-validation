package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;
    private static User registeredUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("uniqueLogin");
        user.setPassword("uniquePassword");
        user.setAge(20);
    }

    @Test
    void register_userAlreadyExists_NotOk() {
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_withNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_withEmptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_withNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_withEmptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginSixChars_Ok() {
        user.setLogin("unique");
        assertEquals(6, registrationService.register(user).getLogin().length());
    }

    @Test
    void register_passwordSixChars_Ok() {
        user.setPassword("unique");
        assertEquals(6, registrationService.register(user).getPassword().length());
    }

    @Test
    void register_userAgeEighteen_Ok() {
        user.setAge(18);
        assertEquals(18, registrationService.register(user).getAge());
    }

    @Test
    void register_userAgeSeventeen_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeTwenty_Ok() {
        user.setAge(20);
        assertEquals(20, registrationService.register(user).getAge());
    }
}
