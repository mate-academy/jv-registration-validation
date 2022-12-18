package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDaoImpl storageDao;
    private static RegistrationServiceImpl registration;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registration = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Bob");
        user.setPassword("123456");
        user.setAge(20);
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        checkAssert(user);
    }

    @Test
    void register_loginEmpty_notOk() {
        user.setLogin("");
        checkAssert(user);
    }

    @Test
    void register_login_Ok() throws ValidationException {
        user.setLogin("Alice");
        registration.register(user);
        assertEquals(storageDao.get("Alice").getLogin(), user.getLogin());
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        checkAssert(user);
    }

    @Test
    void register_passwordMoreFive_notOk() {
        user.setPassword("12345");
        checkAssert(user);
    }

    @Test
    void register_password_Ok() throws ValidationException {
        user.setLogin("John");
        user.setAge(52);
        user.setPassword("@hello#$");
        registration.register(user);
        assertEquals(storageDao.get("John").getPassword(), user.getPassword());
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        checkAssert(user);
    }

    @Test
    void register_ageMoreSeventeen_notOk() {
        user.setAge(0);
        checkAssert(user);
    }

    @Test
    void register_age_Ok() throws ValidationException {
        user.setLogin("Donny");
        user.setPassword("Donny_100");
        user.setAge(100);
        registration.register(user);
        assertEquals(storageDao.get("Donny").getAge(), user.getAge());
    }

    @Test
    void register_userSuccessfullyAdded_Ok() throws ValidationException {
        user.setLogin("Don");
        user.setPassword("123456_Don");
        user.setAge(19);
        registration.register(user);
        assertEquals(user, storageDao.get("Don"));
    }

    private void checkAssert(User user) {
        assertThrows(ValidationException.class, () -> {
            registration.register(user);
        });
    }
}
