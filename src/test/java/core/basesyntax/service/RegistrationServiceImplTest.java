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
    private static User initUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        initUser = new User();
    }

    @BeforeEach
    void setUp() {
        initUser.setLogin("Elvis");
        initUser.setAge(18);
        initUser.setPassword("_elviS");
    }

    @Test
    void registerLogin_is_null_notOk() {
        initUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(initUser));
    }

    @Test
    void registerLogin_is_empty_notOk() {
        initUser.setLogin("");
        assertThrows(NullPointerException.class, () -> registrationService.register(initUser));
    }

    @Test
    void register_user_already_exists_NotOK() {
        registrationService.register(initUser);
        initUser.setLogin("Elvis");
        assertThrows(RuntimeException.class, () -> registrationService.register(initUser));
    }

    @Test
    void register_validUser_Ok() {
        User userTest1 = registrationService.register(initUser);
        assertTrue(Storage.people.contains(userTest1));
    }

    @Test
    void register_age_more_max_Ok() {
        initUser.setLogin("Elvis2");
        initUser.setAge(19);
        User userTest2 = registrationService.register(initUser);
        assertTrue(Storage.people.contains(userTest2));
    }

    @Test
    void register_age_Less_min_NotOk() {
        initUser.setLogin("youngElvis");
        initUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(initUser));
    }

    @Test
    void register_age_is_null_notOk() {
        initUser.setAge(null);;
        assertThrows(NullPointerException.class, () -> registrationService.register(initUser));
    }

    @Test
    void register_password_less_Min_notOk() {
        initUser.setPassword("55555");
        assertThrows(RuntimeException.class, () -> registrationService.register(initUser));
    }

    @Test
    void register_passwordLen_more_max_Ok() {
        initUser.setPassword("1234567");
        User userTest3 = registrationService.register(initUser);
        assertTrue(Storage.people.contains(userTest3));
    }

    @Test
    void register_nullPassword_NotOk() {
        initUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(initUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
