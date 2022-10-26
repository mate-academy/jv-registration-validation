package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationServiceImpl;
    private static final String VALID_USER_LOGIN = "qwerty";
    private static final int VALID_USER_AGE = 18;
    private static final int NOT_VALID_USER_AGE = 15;
    private static final int NEGATIVE_USER_AGE = -15;
    private static final String VALID_PASSWORD = "InViNcIbLePaSsWoRd";
    private static final String NOT_VALID_PASSWORD = "weak";
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setLogin(VALID_USER_LOGIN);
        validUser.setAge(VALID_USER_AGE);
        validUser.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationServiceImpl.register(validUser);
        assertEquals(validUser, actual);
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_alreadyExist_notOk() {
        Storage.people.add(validUser);
        assertThrows(RuntimeException.class, () ->
                  registrationServiceImpl.register(validUser));
    }

    @Test
    void register_age_notOk() {
        validUser.setAge(NOT_VALID_USER_AGE);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(validUser));
    }

    @Test
    void register_ageNegative_notOk() {
        validUser.setAge(NEGATIVE_USER_AGE);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @Test
    void register_password_notOk() {
        validUser.setPassword(NOT_VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        validUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @Test
    void register_ageNull_NotOk() {
        validUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @Test
    void register_passwordNull_NotOk() {
        validUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
