package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "Nikitos";
    private static final long DEFAULT_ID = 1L;
    private static final int DEFAULT_AGE = 26;
    private static final String DEFAULT_PASSWORD = "qwertyu1213";
    private static final String UNCORRECT_PASSWORD = "wsw";
    private static final int MIN_AGE = 18;
    private static final String EMPTY = "";
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setId(DEFAULT_ID);
    }

    @Test
    void register_UserIsNull_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
    }
    
    @Test
    void register_LoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginIsEmpty_NotOk() {
        user.setLogin(EMPTY);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserAlreadyExist_NotOk() {
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeIsNull_NotOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeLessThan18_NotOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LengthOfPasswordLessThanMin_NotOk() {
        user.setPassword(UNCORRECT_PASSWORD);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordIsEmpty_NotOk() {
        user.setPassword(EMPTY);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_SuccessfulRegistration_Ok() {
        Storage.people.add(user);
        assertEquals(Storage.people.get(0).getAge(), DEFAULT_AGE);
        assertEquals(Storage.people.get(0).getPassword(), DEFAULT_PASSWORD);
        assertEquals(Storage.people.get(0).getLogin(), DEFAULT_LOGIN);
        assertEquals(Storage.people.get(0).getId(), DEFAULT_ID);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
