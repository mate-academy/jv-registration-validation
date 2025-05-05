package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "name@gmail";
    private static final String VALID_PASSWORD = "1234678";
    private static final int VALID_AGE = 18;
    private static User user;
    private static RegistrationService registrationService;
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThan18_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_ok() {
        user.setLogin("nadia@gmail");
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
        assertNotNull(user.getId());
    }

    @Test
    void register_loginExisting_notOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
