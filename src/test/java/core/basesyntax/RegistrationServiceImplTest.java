package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        Storage.PEOPLE_LIST.clear();
    }

    @Test
    void register_User_Ok() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("password23");
        user.setAge(23);
        Storage.PEOPLE_LIST.add(user);
        assertTrue(Storage.PEOPLE_LIST.contains(user));
    }

    @Test
    void register_User_NullValue_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_UserExisting_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("password23");
        user.setAge(23);
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginShort_notOk() {
        User user = new User();
        user.setLogin("u");
        user.setPassword("password23");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginNull_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password23");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginEmpty_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("password23");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginMin_Ok() {
        User user = new User();
        user.setLogin("userLo");
        user.setPassword("password23");
        user.setAge(23);
        assertTrue(registrationService.register(user) != null);
    }

    @Test
    void register_PasswordShort_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("p");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordNull_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword(null);
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordEmpty_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordMin_Ok() {
        User user = new User();
        user.setLogin("userLogin102");
        user.setPassword("passwo");
        user.setAge(23);
        assertTrue(registrationService.register(user) != null);
    }

    @Test
    void register_AgeMin_Ok() {
        User user = new User();
        user.setLogin("user12347");
        user.setPassword("userPassword101");
        user.setAge(18);
        assertTrue(registrationService.register(user) != null);
    }

    @Test
    void register_Age_Ok() {
        User user = new User();
        user.setLogin("user12345");
        user.setPassword("userPassword101");
        user.setAge(19);
        assertTrue(registrationService.register(user) != null);
    }

    @Test
    void register_Age_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("userPassword101");
        user.setAge(13);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeNegative_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("userPassword101");
        user.setAge(-13);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeNull_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("userPassword101");
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
