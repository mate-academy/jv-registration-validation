package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_USER_LOGIN = "qwerty";
    private static final int VALID_USER_AGE = 18;
    private static final int NOT_VALID_USER_AGE = 15;
    private static final int NEGATIVE_USER_AGE = -15;
    private static final String VALID_PASSWORD = "InViNcIbLePaSsWoRd";
    private static final String NOT_VALID_PASSWORD = "weak";

    private RegistrationServiceImpl registrationServiceImpl;
    private User validUser;

    @BeforeEach
    void setUp() {
        registrationServiceImpl = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setLogin(VALID_USER_LOGIN);
        validUser.setAge(VALID_USER_AGE);
        validUser.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationServiceImpl.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_alreadyExist_notOk() {
        registrationServiceImpl.register(validUser);
        User userDuplicate = new User();
        userDuplicate.setLogin(validUser.getLogin());
        userDuplicate.setPassword(validUser.getPassword());
        userDuplicate.setAge(validUser.getAge());
        assertThrows(RuntimeException.class, () ->
            registrationServiceImpl.register(userDuplicate));
    }

    @Test
    void register_ageCheck_notOk() {
        validUser.setAge(NOT_VALID_USER_AGE);
        assertThrows(RuntimeException.class, () ->
            registrationServiceImpl.register(validUser));
    }

    @Test
    void register_ageNegativeCheck_notOk() {
        validUser.setAge(NEGATIVE_USER_AGE);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @Test
    void register_passwordCheck_notOk() {
        validUser.setPassword(NOT_VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(null));
    }

    @Test
    void register_loginNullCheck_NotOk() {
        validUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @Test
    void register_ageNullCheck_NotOk() {
        validUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @Test
    void register_passwordNullCheck_NotOk() {
        validUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(validUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
