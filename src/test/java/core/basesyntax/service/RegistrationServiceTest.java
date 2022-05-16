package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userUnderMinAge_notOk() {
        User user = new User("user", "123456", 17);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeNegative_notOk() {
        User user = new User("user", "123456", -1);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordUnderMinLength_notOk() {
        User user = new User("user", "12345", 18);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_alreadyExistedLogin_notOk() {
        User loginAlreadyExist = new User("LoginAlreadyExist", "123456", 19);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(loginAlreadyExist);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(loginAlreadyExist);
        });
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userLoginNull_notOk() {
        User user = new User(null, "123456", 18);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userPasswordNull_notOk() {
        User user = new User("user", null, 18);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeNull_notOk() {
        User user = new User("user", "123456", null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validUser_Ok() {
        User correctFields = new User("CorrectFields", "123456", 18);

        User actual = registrationService.register(correctFields);

        assertEquals(correctFields, actual);
    }

    @Test
    void register_userWithMinAge_Ok() {
        User minAge = new User("age18", "123456", 18);

        User actual = registrationService.register(minAge);

        assertEquals(minAge, actual);
    }

    @Test
    void register_userAgeOlderThanMinAge_Ok() {
        User olderThanMinAge = new User("OlderThan18", "123456", 22);

        User actual = registrationService.register(olderThanMinAge);

        assertEquals(olderThanMinAge, actual);
    }

    @Test
    void register_userPasswordContainsMinLength_Ok() {
        User passwordContainsMinLength = new User("PasswordContainsSixSymbols", "123456", 22);

        User actual = registrationService.register(passwordContainsMinLength);

        assertEquals(passwordContainsMinLength, actual);
    }

    @Test
    void register_userPasswordContainsMoreThanMinLength_Ok() {
        User passwordContainsMoreThanMinLength
                = new User("PasswordContainsMoreThanSixSymbols", "123456789", 22);

        User actual = registrationService.register(passwordContainsMoreThanMinLength);

        assertEquals(passwordContainsMoreThanMinLength, actual);
    }

    @Test
    void register_userAddToStorage_Ok() {
        User userAddToStorage = new User("userAddToStorage", "123456", 30);
        User actual = registrationService.register(userAddToStorage);

        StorageDaoImpl storageDao = new StorageDaoImpl();
        User expected = storageDao.get("userAddToStorage");

        assertEquals(expected, actual);
    }

    @Test
    void register_emptyUserLogin_notOk() {
        User user = new User("", "123456", 18);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
