package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final Integer MIN_REGISTRATION_AGE = 18;
    private static final Integer MAX_REGISTRATION_AGE = 100;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("sounddummies");
        user.setAge(MIN_REGISTRATION_AGE);
        user.setPassword("Genius1988");
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual, "Valid user is not registered!");
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userWithSameLoginIsAlreadyExist_notOk() {
        StorageDao storage = new StorageDaoImpl();
        storage.add(user);
        User userWithSameLogin = new User();
        userWithSameLogin.setLogin("sounddummies");
        userWithSameLogin.setAge(MIN_REGISTRATION_AGE);
        userWithSameLogin.setPassword("Genius1988");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithSameLogin));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageLess18_notOk() {
        user.setAge(MIN_REGISTRATION_AGE - 1);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_UserIsNull_notOK() {
        User user2 = new User();
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void register_PasswordIncorrect_notOK() {
        user.setPassword("OLX");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
