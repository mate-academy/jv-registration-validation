package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static Storage storage;
    private User userAlexTest;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new Storage();
    }

    @BeforeEach
    void setUp() {
        userAlexTest = new User();
        userAlexTest.setPassword("12345User");
        userAlexTest.setLogin("AlexTest");
        userAlexTest.setAge(21);
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userAgeTooYoung_notOk() {
        userAlexTest.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(userAlexTest));
    }

    @Test
    void register_userAgeTooBig_notOk() {
        userAlexTest.setAge(110);
        assertThrows(RuntimeException.class, () -> registrationService.register(userAlexTest));
    }

    @Test
    void register_userPassTooShort_notOk() {
        userAlexTest.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(userAlexTest));
    }

    @Test
    void register_login_notOk() {
        User userBroTest = new User();
        userBroTest.setLogin("12345User");
        userBroTest.setAge(21);
        userBroTest.setPassword("123456LL");
        storage.people.add(userBroTest);
        assertThrows(RuntimeException.class, () -> registrationService.register(userBroTest));
    }

    @Test
    void register_nullFields_notOk() {
        userAlexTest.setAge(null);
        userAlexTest.setPassword(null);
        userAlexTest.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userAlexTest));
    }

    @AfterEach
    void afterEach() {
        storage.people.clear();
    }
}
