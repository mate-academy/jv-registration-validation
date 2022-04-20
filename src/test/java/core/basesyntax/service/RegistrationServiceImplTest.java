package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User firstValidUser;
    private User userWithNullProperties;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        firstValidUser = new User("koko@gmail.com", "12345678", 23);
        userWithNullProperties = new User(null, null, 0);
    }

    @AfterEach
    void cleanUp() {
        Storage.people.clear();
    }

    @Test
    void register_userAlreadyWasAdded_NotOk() {
        User sameAsFirstValidUser = new User("koko@gmail.com", "332322569", 20);
        registrationService.register(firstValidUser);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(sameAsFirstValidUser));
    }

    @Test
    void register_userOlder_NotOk() {
        User noValidAgeUser = new User("loko@gmail.com", "129456", 17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(noValidAgeUser));
    }

    @Test
    void register_passwordLength_NotOk() {
        User noValidPasswordUser = new User("boko@gmail.com", "02345", 52);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(noValidPasswordUser));
    }

    @Test
    void registerUser_Ok() {
        User actual = registrationService.register(firstValidUser);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userWithNullProperties));
    }

    @Test
    void register_loginIsNull_NotOk() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userWithNullProperties));
    }

    @Test
    void register_ageIsNull_NotOk() {
        registrationService.register(firstValidUser);
        firstValidUser.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(firstValidUser));
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(null));
    }
}
