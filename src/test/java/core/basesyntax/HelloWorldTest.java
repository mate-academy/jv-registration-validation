package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User uniqUser;
    private static User sameUser;
    private static User differentUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        uniqUser = new User();
        sameUser = new User();
        differentUser = new User();
    }

    @BeforeEach
    void setUp() {
        uniqUser.setLogin("testUser");
        uniqUser.setId(12L);
        uniqUser.setAge(25);
        uniqUser.setPassword("userPassword1");

        sameUser.setLogin("testUser");
        sameUser.setId(12L);
        sameUser.setAge(25);
        sameUser.setPassword("userPassword1");

        differentUser.setLogin("testUser2");
        differentUser.setId(15L);
        differentUser.setAge(25);
        differentUser.setPassword("userPassword2");
    }

    @AfterEach
    void tearDown() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_nullLogin_notOK() {
        uniqUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_nullInput_notOK() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userAgeLessThanNeed_notOK() {
        uniqUser.setAge(12);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_nullPassword_notOK() {
        uniqUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_nullAge_notOK() {
        uniqUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_nullID_notOK() {
        uniqUser.setId(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_lessThanNullID_notOK() {
        uniqUser.setId(-10L);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_userWithSuchLogin_notOK() {
        boolean sameLogin = uniqUser.getLogin().equals(sameUser.getLogin());
        assertTrue(sameLogin);
        storageDao.add(uniqUser);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(sameUser);
        });
        assertEquals(uniqUser, storageDao.get(uniqUser.getLogin()));
    }

    @Test
    void register_incorrectPasswordLength_notOk() {
        uniqUser.setPassword("pass1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_userAgeEqualsNeedAge_OK() {
        uniqUser.setAge(18);
        storageDao.add(uniqUser);
    }

    @Test
    void register_userAgeMoreThanNeed_OK() {
        uniqUser.setAge(18);
        storageDao.add(uniqUser);
    }

    @Test
    void register_userAgeLessThanNull_notOK() {
        uniqUser.setAge(-30);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_correctPasswordLength_Ok() {
        uniqUser.setPassword("correct");
        storageDao.add(uniqUser);
    }

    @Test
    void register_correctUser_OK() {
        storageDao.add(uniqUser);
    }

    @Test
    void register_differentUsers_Ok() {
        boolean differentLogin = uniqUser.getLogin()
                .equals(differentUser.getLogin());
        assertFalse(differentLogin);
        storageDao.add(uniqUser);
        storageDao.add(differentUser);
        assertEquals(uniqUser,storageDao.get(uniqUser.getLogin()));
        assertEquals(differentUser, storageDao.get(differentUser.getLogin()));
    }

}
