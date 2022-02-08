package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
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
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword("123456");
        user.setId(1L);
        user.setAge(24);
        user.setLogin("Alice");
    }

    @Test
    void register_userIsNull_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
        void register_userIsLess18_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
  void register_passwordIsLess6Letters_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
  void register_addExistingLoginUser_notOk() {
        Storage.people.add(user);
        user.setLogin("Alice");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
  void register_addExistingLoginUserUpperCase_notOk() {
        Storage.people.add(user);
        user.setLogin("aLLice");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
  void registerAdd_correctInput_ok() {
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
  void register_loginValueIsNull_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
  void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
  void register_userNegativeAge_notOk() {
        user.setAge(-26);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
  void register_userAgeOver18_ok() {
        user.setAge(39);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @AfterEach
  void tearDown() {
        Storage.people.clear();
    }
}
