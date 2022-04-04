package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(3364L, "admin", "pass123", 18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userValid_Ok() {
        User actual = registrationService.register(user);
        User userInStorage = storageDao.get(user.getLogin());
        assertEquals(user, actual);
        assertEquals(user, userInStorage);
    }

    @Test
    void register_wrongAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPass_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullId_notOk() {
        user.setId(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPass_notOk() {
        user.setPassword("abc");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
