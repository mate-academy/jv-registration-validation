package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    User user1;
    User user2;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user1 = new User();
        user2 = new User();
        user1.setAge(23);
        user1.setLogin("mylogin");
        user1.setPassword("SuperPassword666");
        user2.setAge(40);
        user2.setLogin("Vasya81");
        user2.setPassword("Password81");
    }

    @Test
    void register_allFieldsAreValid_Ok() {
        registrationService.register(user1);
        registrationService.register(user2);
        assertTrue(user1.equals(storageDao.get(user1.getLogin()))
                && user2.equals(storageDao.get(user2.getLogin())));
    }

    @Test
    void register_LoginAlreadyExists_notOk() {
        user1.setLogin("Vasya81");
        registrationService.register(user1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_loginIsNull_notOk() {
        user1.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }


    @Test
    void register_loginIsShorterThan6_notOk() {
        user1.setLogin("login");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_loginNotOnlyLettersNumbers_notOk() {
        user1.setLogin("log!@#$%^&*()<>?in");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_ageIsIllegal_notOk() {
        user1.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_ageIsFantastic_notOk() {
        user1.setAge(120);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_ageIsNegative_notOk() {
        user1.setAge(-3);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_ageIs18_notOk() {
        user1.setAge(18);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_ageIsNull_notOk() {
        user1.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_pwdIsShorterThan6_notOk() {
        user1.setPassword("Pass1");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_pwdNotOnlyLettersDigitsCaps_notOk() {
        user1.setPassword("Pass10!@#$%^&*()><?");
        user2.setPassword("+=_:;dek83F");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_pwdNotOnlyLettersDigitsCaps2_notOk() {
        user1.setPassword("+=_:;dek83F");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_pwdNoCaps_notOk() {
        user1.setPassword("password1");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_pwdNoNumbers_notOk() {
        user1.setPassword("PassWord");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_PwdIsNull_notOk() {
        user1.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }
}