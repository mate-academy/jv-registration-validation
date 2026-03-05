package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;
    private StorageDao storageDao;


    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
    }

    @Test
    void register_userHasBeenAdded_Ok() {
        User user = new User();
        user.setId(12L);
        user.setLogin("patryk");
        user.setAge(20);
        user.setPassword("secret");
        registrationService.register(user);
        User userFromStorage = storageDao.get("patryk");
        assertNotNull(userFromStorage, "User should be saved in the storage");
        assertEquals("patryk", userFromStorage.getLogin());
    }

    @Test
    void register_validUser_Ok() {
        User user = new User();
        user.setId(12L);
        user.setLogin("patryk");
        user.setAge(20);
        user.setPassword("secret");
        User result = registrationService.register(user);
        assertNotNull(result);
        assertEquals("patryk", result.getLogin());
    }

    @Test
    void register_loginIsTooShort_notOk() {
        User user = new User();
        user.setId(15L);
        user.setLogin("abc");
        user.setPassword("secret1234");
        user.setAge(26);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthIsUnderMinimum_notOk() {
        User user = new User();
        user.setId(15L);
        user.setLogin("abcde");
        user.setPassword("secret1234");
        user.setAge(26);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthIsExactlyMinimum_ok() {
        User user = new User();
        user.setId(15L);
        user.setLogin("abcdef");
        user.setPassword("secret1234");
        user.setAge(26);
        User result = registrationService.register(user);
        assertEquals("abcdef", result.getLogin());
    }

    @Test
    void register_passwordIsTooShort_notOk() {
        User user = new User();
        user.setId(20L);
        user.setLogin("login123");
        user.setPassword("secr");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthIsExactlyMinimum_ok() {
        User user = new User();
        user.setId(15L);
        user.setLogin("abcdef");
        user.setPassword("secret");
        user.setAge(26);
        User result = registrationService.register(user);
        assertEquals("secret", result.getPassword());
    }

    @Test
    void register_passwordLengthIsUnderMinimum_notOk() {
        User user = new User();
        user.setId(15L);
        user.setLogin("abcdef");
        user.setPassword("secre");
        user.setAge(26);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsTooYoung_notOk() {
        User user = new User();
        user.setId(20L);
        user.setLogin("login123");
        user.setPassword("secr");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsExactlyEighteen_ok() {
        User user = new User();
        user.setId(20L);
        user.setLogin("login123");
        user.setPassword("secr122");
        user.setAge(18);
        User result = registrationService.register(user);
        assertEquals(18, result.getAge());
    }

    @Test
    void register_userHasNegativeAge_notOk() {
        User user = new User();
        user.setId(20L);
        user.setLogin("login123");
        user.setPassword("secr");
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        User user = new User();
        user.setId(18L);
        user.setLogin("login123");
        user.setPassword("secr1233");
        user.setAge(23);
        storageDao.add(user);
        User anotherUser = new User();
        anotherUser.setId(20L);
        anotherUser.setLogin("login123");
        anotherUser.setPassword("secr1233123");
        anotherUser.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(anotherUser));
    }

    @Test
    void register_userAgeIsNull_notOk() {
        User user = new User();
        user.setId(20L);
        user.setLogin("login123");
        user.setPassword("secr2222");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsNull_notOk() {
        User user = new User();
        user.setId(20L);
        user.setLogin(null);
        user.setPassword("secr2222");
        user.setAge(33);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        User user = new User();
        user.setId(20L);
        user.setLogin("Patryk12345");
        user.setPassword(null);
        user.setAge(33);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
