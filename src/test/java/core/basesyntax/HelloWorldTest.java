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

    private static final int AGE = 25;
    private static final long ID = 10L;
    private static final int MIN_AGE = 18;
    private static final int MINUS_AGE = -18;
    private static final int LESS_THAN_NEED_AGE = -18;
    private static final String USER_LOGIN_ONE = "testUser";
    private static final String USER_LOGIN_TWO = "testUser2";
    private static final String CORRECT_PASSWORD = "userPassword";
    private static final String CORRECT_PASSWORD2 = "userPass666";
    private static final String BAD_PASSWORD = "user";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        uniqUser = new User();

        sameUser = new User();
        sameUser.setLogin(USER_LOGIN_ONE);
        sameUser.setId(ID);
        sameUser.setAge(AGE);
        sameUser.setPassword(CORRECT_PASSWORD);

        differentUser = new User();
        differentUser.setLogin(USER_LOGIN_TWO);
        differentUser.setId(ID);
        differentUser.setAge(AGE);
        differentUser.setPassword(CORRECT_PASSWORD);
    }

    @BeforeEach
    void setUp() {
        uniqUser.setLogin(USER_LOGIN_ONE);
        uniqUser.setId(ID);
        uniqUser.setAge(AGE);
        uniqUser.setPassword(CORRECT_PASSWORD);
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
        uniqUser.setAge(LESS_THAN_NEED_AGE);
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
        uniqUser.setPassword(BAD_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_userAgeEqualsNeedAge_OK() {
        uniqUser.setAge(MIN_AGE);
        storageDao.add(uniqUser);
    }

    @Test
    void register_userAgeMoreThanNeed_OK() {
        uniqUser.setAge(AGE);
        storageDao.add(uniqUser);
    }

    @Test
    void register_userAgeLessThanNull_notOK() {
        uniqUser.setAge(MINUS_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(uniqUser);
        });
    }

    @Test
    void register_correctPasswordLength_Ok() {
        uniqUser.setPassword(CORRECT_PASSWORD2);
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
