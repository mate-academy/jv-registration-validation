package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
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
    static void beforeAll() throws InvalidDataException {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
        user.setLogin("uniqueLogin");
        user.setPassword("uniquePassword");
        user.setId(3L);
        user.setAge(20);
        registeredUser = registrationService.register(user);
    }

    @BeforeEach
    void setUp() {
        storageDao.add(user);
        storageDao.add(registeredUser);
    }

    @Test
    void register_noSameLoginStorage_Ok() {
        assertEquals(user, registeredUser);
        User fromStorage = storageDao.get("uniqueLogin");
        assertEquals(user, fromStorage);
    }

    @Test
    void register_withNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_withEmptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_withNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_withEmptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginSixChars_Ok() {
        user.setLogin("unique");
        assertEquals(user.getLogin().length(), registeredUser.getLogin().length());
    }

    @Test
    void register_passwordSixChars_Ok() {
        user.setPassword("unique");
        assertEquals(6, registeredUser.getPassword().length());
    }

    @Test
    void register_userAgeEighteen_Ok() {
        user.setAge(18);
        assertEquals(18, registeredUser.getAge());
    }

    @Test
    void register_userAgeSeventeen_NotOk() {
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeTwenty_Ok() {
        user.setAge(20);
        assertEquals(20, registeredUser.getAge());
    }
}
