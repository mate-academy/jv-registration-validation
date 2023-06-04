package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User validUser;
    private static final String INVALID_PASSWORD = "12345";
    private static final String VALID_PASSWORD = "1234567";
    private static final String CORRECT_LOGIN = "Ivan Ivanov";
    private static final String NOT_CORRECT_LOGIN = "Nike";
    private static final int CORRECT_AGE = 22;
    private static final int NOT_CORRECT_AGE = 15;
    private static final int NULL_AGE = 0;
    private static RegistrationService registrationService;
    private static final String EXCEPTION_MESSAGE =
            "RegistrationException should be thrown in this case.";

    @BeforeAll
    static void init() {

        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User(CORRECT_LOGIN, VALID_PASSWORD, CORRECT_AGE);
    }

    @Test
    void register_nullAge_notOk() {
        validUser = new User(CORRECT_LOGIN, VALID_PASSWORD, NULL_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser), EXCEPTION_MESSAGE);
    }

    @Test
    void register_nullLogin_notOk() {
        validUser = new User(null, VALID_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser), EXCEPTION_MESSAGE);
    }

    @Test
    void register_userAlreadyExists_notOk() {
        validUser = new User(CORRECT_LOGIN, INVALID_PASSWORD, CORRECT_AGE);
        Storage.people.add(validUser);
        User userReg = new User(CORRECT_LOGIN, INVALID_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userReg), EXCEPTION_MESSAGE);
    }

    @Test
    void register_shortPassword_notOk() {
        validUser = new User(CORRECT_LOGIN, INVALID_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser), EXCEPTION_MESSAGE);
    }

    @Test
    void register_lessAge_notOk() {
        validUser = new User(CORRECT_LOGIN, VALID_PASSWORD, NOT_CORRECT_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser), EXCEPTION_MESSAGE);
    }

    @Test
    void register_validData_ok() {
        int size = Storage.people.size();
        validUser = new User(NOT_CORRECT_LOGIN, VALID_PASSWORD, CORRECT_AGE);
        registrationService.register(validUser);
        assertEquals(size + 1, Storage.people.size());
    }

    @AfterEach
    public void afterEach() {
        Storage.people.clear();
    }
}
