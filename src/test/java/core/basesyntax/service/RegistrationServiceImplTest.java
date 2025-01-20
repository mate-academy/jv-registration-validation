package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static final String VALID_LOGIN = "username";
    private static final String INVALID_LOGIN = "name";
    private static final String VALID_PASSWORD = "password";
    private static final String INVALID_PASSWORD = "pass";
    private static final int VALID_AGE = 22;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = createValidUser();
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        User duplicatedUser = createValidUser();
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> service.register(duplicatedUser));
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLengthGreaterThanSix_ok() {
        User registeredUser = service.register(user);
        compareExpectedActualUsers(registeredUser);
    }

    @Test
    void register_loginLengthLessThanSix_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordLengthGreaterThanSix_ok() {
        User registeredUser = service.register(user);
        compareExpectedActualUsers(registeredUser);
    }

    @Test
    void register_passwordLengthLessThanSix_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageIsLessThan18_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageIsGreaterThan18_ok() {
        User registeredUser = service.register(user);
        compareExpectedActualUsers(registeredUser);
    }

    private static User createValidUser() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        return user;
    }

    private void compareExpectedActualUsers(User expected) {
        assertAll(
                () -> assertEquals(user.getLogin(), expected.getLogin()),
                () -> assertEquals(user.getPassword(), expected.getPassword()),
                () -> assertEquals(user.getAge(), expected.getAge())
        );
    }
}
