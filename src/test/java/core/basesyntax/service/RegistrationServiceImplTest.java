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
    private static final String DEFAULT_TEST_LOGIN = "George_Washington";
    private static final String DEFAULT_TEST_PASSWORD = "AmericanFather";
    private static final int DEFAULT_TEST_AGE = 57;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(DEFAULT_TEST_LOGIN, DEFAULT_TEST_PASSWORD, DEFAULT_TEST_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_appropriateUser_Ok() {
        User actual = user;
        User expected = registrationService.register(actual);
        assertEquals(expected, actual, "A user with the relevant criteria must be registered.");
    }

    @Test
    void register_incorrectAge_NotOk() {
        user.setAge(17);
        expectException(user);
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-1);
        expectException(user);
    }

    @Test
    void register_loginHasBeenAlreadyCreated_NotOk() {
        registrationService.register(user);
        expectException(user);
    }

    @Test
    void register_passwordIsLessThan6Characters_NotOk() {
        user.setPassword("amrcn");
        expectException(user);
    }

    @Test
    void register_userIsNull_NotOk() {
        expectException(null);
    }

    @Test
    void register_loginIsNull_NotOk() {
        user.setLogin(null);
        expectException(user);
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        expectException(user);
    }

    @Test
    void register_ageIsNull_NotOk() {
        user.setAge(null);
        expectException(user);
    }

    private void expectException(User user) {
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }
}
