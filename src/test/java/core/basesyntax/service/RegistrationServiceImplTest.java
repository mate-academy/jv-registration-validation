package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User validUser;
    private static final String VALID_PASSWORD = "12345";
    private static final String INVALID_PASSWORD = "1234567";
    private static RegistrationService registrationService;
    private static final String EXCEPTION_MESSAGE =
            "RegistrationException should be thrown in this case.";

    @BeforeEach
    void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullAge_notOk() {
        validUser = new User("Bob", VALID_PASSWORD, 0);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),EXCEPTION_MESSAGE);
    }

    @Test
    void register_nullLogin_notOk() {
        validUser = new User(null, INVALID_PASSWORD, 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),EXCEPTION_MESSAGE);
    }

    @Test
    void register_userAlreadyExists_notOk() {
        validUser = new User("Ivan ivanov", INVALID_PASSWORD, 21);
        Storage.people.add(validUser);
        User userReg = new User("Ivan ivanov", INVALID_PASSWORD, 21);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userReg),EXCEPTION_MESSAGE);
    }

    @Test
    void register_shortPassword_notOk() {
        validUser = new User("Jane", VALID_PASSWORD, 22);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),EXCEPTION_MESSAGE);
    }

    @Test
    void register_lessAge_notOk() {
        validUser = new User("Nike", VALID_PASSWORD, 15);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),EXCEPTION_MESSAGE);
    }

    @Test
    void register_validData() {
        int size = Storage.people.size();
        validUser = new User("Nike", INVALID_PASSWORD, 22);
        registrationService.register(validUser);
        assertEquals(size + 1, Storage.people.size());
    }

    @AfterEach
    public void afterEach() {
        Storage.people.clear();
    }
}
