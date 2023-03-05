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
    private static final int DEFAULT_AGE = 26;
    private static final String DEFAULT_PASSWORD = "qwertyu1213";
    private static final int MAX_AGE = 100;
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
        registrationService.register(user);
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
        user.setAge(MIN_AGE - 7);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeMoreThanMax_NotOk() {
        user.setAge(MAX_AGE + 100);
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
        user.setPassword("sws");
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
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin("Jordan");
        newUser.setAge(66);
        newUser.setPassword("sqwwrrws3");
        registrationService.register(newUser);
        assertEquals(Storage.people.get(0), user);
        assertEquals(Storage.people.get(1), newUser);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
