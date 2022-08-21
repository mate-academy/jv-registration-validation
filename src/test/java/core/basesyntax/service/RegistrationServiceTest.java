package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void registerValidUser_ok() {
        User correctUser = new User();
        correctUser.setId(4L);
        correctUser.setLogin("correctUser");
        correctUser.setPassword("444444");
        correctUser.setAge(18);
        assertNotNull(registrationService.register(correctUser));
    }

    @Test
    void registerExistingUser_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(Storage.people.get(0)));
    }

    @Test
    void registerUserNullLogin_notOk() {
        User incorrectUserNullLogin = new User();
        incorrectUserNullLogin.setId(4L);
        incorrectUserNullLogin.setLogin(null);
        incorrectUserNullLogin.setPassword("444444");
        incorrectUserNullLogin.setAge(21);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(incorrectUserNullLogin));
    }

    @Test
    void registerUserNullPassword_notOk() {
        User incorrectUserNullPassword = new User();
        incorrectUserNullPassword.setId(4L);
        incorrectUserNullPassword.setLogin("testUserNullPassword");
        incorrectUserNullPassword.setPassword(null);
        incorrectUserNullPassword.setAge(21);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(incorrectUserNullPassword));
    }

    @Test
    void registerUserShortPassword_notOk() {
        User incorrectUserShortPassword = new User();
        incorrectUserShortPassword.setId(4L);
        incorrectUserShortPassword.setLogin("testUserShortPassword");
        incorrectUserShortPassword.setPassword("444");
        incorrectUserShortPassword.setAge(21);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(incorrectUserShortPassword));
    }

    @Test
    void registerUserLessAge_notOk() {
        User incorrectUserLessAge = new User();
        incorrectUserLessAge.setId(4L);
        incorrectUserLessAge.setLogin("testUserLessAge");
        incorrectUserLessAge.setPassword("444444");
        incorrectUserLessAge.setAge(15);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(incorrectUserLessAge));
    }

    @Test
    void registerUserNullAge_notOk() {
        User incorrectUserNullAge = new User();
        incorrectUserNullAge.setId(4L);
        incorrectUserNullAge.setLogin("testUserNullAge");
        incorrectUserNullAge.setPassword("444444");
        incorrectUserNullAge.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(incorrectUserNullAge));
    }

    @Test
    void registerUserNegativeAge_notOk() {
        User incorrectUserNegativeAge = new User();
        incorrectUserNegativeAge.setId(4L);
        incorrectUserNegativeAge.setLogin("testUserNegativeAge");
        incorrectUserNegativeAge.setPassword("444444");
        incorrectUserNegativeAge.setAge(-15);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(incorrectUserNegativeAge));
    }

    @Test
    void check_userIsNull_ok() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }
}
