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
        assertThrows(ValidationException.class, () -> registration.register(user));
    }

    @Test
    void register_loginEmpty_notOk() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> registration.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> registration.register(user));
    }

    @Test
    void register_passwordMoreFive_notOk() {
        user.setPassword("12345");
        assertThrows(ValidationException.class, () -> registration.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(ValidationException.class, () -> registration.register(user));
    }

    @Test
    void register_ageMoreSeventeen_notOk() {
        user.setAge(0);
        assertThrows(ValidationException.class, () -> registration.register(user));
    }

    @Test
    void register_userSuccessfullyAdded_Ok() throws ValidationException {
        user.setLogin("Don");
        user.setPassword("123456_Don");
        user.setAge(19);
        registration.register(user);
        assertEquals(user, storageDao.get("Don"));
    }
}
