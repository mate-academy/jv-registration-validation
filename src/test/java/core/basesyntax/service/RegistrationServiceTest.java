package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("Samantha");
        firstUser.setPassword("pas12f");
        User secondUser = new User();
        secondUser.setAge(25);
        secondUser.setLogin("Adam");
        secondUser.setPassword("fort96");
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(firstUser);
        storageDao.add(secondUser);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(30);
        user.setLogin("Sam");
        user.setPassword("garT2!");
    }

    @Test
    void register_validUser_isOk() {
        User validUser = new User();
        validUser.setAge(35);
        validUser.setLogin("Richard");
        validUser.setPassword("123456");
        assertEquals(validUser, registrationService.register(validUser));
    }

    @Test
    void register_nullUser_notOK() {
        user.setLogin(null);
        assertThrows(UserNotValidException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginIsNull_notOK() {
        user.setLogin(null);
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOK() {
        user.setLogin("");
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOK() {
        user.setPassword(null);
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLessThanMinCharacters_notOK() {
        user.setPassword("null");
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageEqualZero_notOK() {
        user.setAge(0);
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOK() {
        user.setAge(-17);
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOK() {
        user.setAge(null);
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThan18_notOK() {
        user.setAge(17);
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageMoreThan90_notOK() {
        user.setAge(91);
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_sameLogin_notOK() {
        user.setLogin("Samantha");
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }
}
