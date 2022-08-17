package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static StorageDao defaultStorage;
    private static RegistrationServiceImpl registrationService;
    private static final String NOT_EXIST_LOGIN = "forth";

    @BeforeAll
    static void beforeAll() {
        defaultStorage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setLogin("first");
        firstUser.setPassword("111111");
        firstUser.setAge(18);
        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setLogin("second");
        secondUser.setPassword("222222");
        secondUser.setAge(19);
        User thirdUser = new User();
        thirdUser.setId(3L);
        thirdUser.setLogin("third");
        thirdUser.setPassword("333333");
        thirdUser.setAge(20);
        Storage.people.add(0, firstUser);
        Storage.people.add(1, secondUser);
        Storage.people.add(2, thirdUser);
    }

    @Test
    void check_userNotExist_ok() {
        assertNull(defaultStorage.get(NOT_EXIST_LOGIN));
    }

    @Test
    void check_register_ok() {
        User testUserOk = new User();
        testUserOk.setId(4L);
        testUserOk.setLogin("testUserOk");
        testUserOk.setPassword("444444");
        testUserOk.setAge(18);
        assertNotNull(registrationService.register(testUserOk));
    }

    @Test
    void check_repeatRegister() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(Storage.people.get(0)));
    }

    @Test
    void check_registerUserInvalidLogin() {
        User testUserNullLogin = new User();
        testUserNullLogin.setId(4L);
        testUserNullLogin.setLogin(null);
        testUserNullLogin.setPassword("444444");
        testUserNullLogin.setAge(21);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUserNullLogin));
    }

    @Test
    void check_registerUserInvalidPassword() {
        User testUserNullPassword = new User();
        testUserNullPassword.setId(4L);
        testUserNullPassword.setLogin("testUserNullPassword");
        testUserNullPassword.setPassword(null);
        testUserNullPassword.setAge(21);
        assertNull(registrationService.register(testUserNullPassword));

        User testUserShortPassword = new User();
        testUserShortPassword.setId(4L);
        testUserShortPassword.setLogin("testUserShortPassword");
        testUserShortPassword.setPassword("444");
        testUserShortPassword.setAge(21);
        assertNull(registrationService.register(testUserShortPassword));
    }

    @Test
    void check_registerUserInvalidAge() {
        User testUserLessAge = new User();
        testUserLessAge.setId(4L);
        testUserLessAge.setLogin("testUserLessAge");
        testUserLessAge.setPassword("444444");
        testUserLessAge.setAge(15);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(testUserLessAge));

        User testUserNullAge = new User();
        testUserNullAge.setId(4L);
        testUserNullAge.setLogin("testUserNullAge");
        testUserNullAge.setPassword("444444");
        testUserNullAge.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(testUserNullAge));

        User testUserNegativeAge = new User();
        testUserNegativeAge.setId(4L);
        testUserNegativeAge.setLogin("testUserNegativeAge");
        testUserNegativeAge.setPassword("444444");
        testUserNegativeAge.setAge(-15);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(testUserNegativeAge));
    }

    @Test
    void check_userIsNull_ok() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }
}
