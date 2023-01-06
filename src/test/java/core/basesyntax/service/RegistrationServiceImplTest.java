package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 18;
    private static final String DEFAULT_PASSWORD = "845146";
    private static final String DEFAULT_LOGIN = "user_login";
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUser() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void ageLessThan18_notOK() {
        user.setAge(DEFAULT_AGE - 1);
        assertThrows(RegistrationException.class,() -> registrationService.register(user));
    }

    @Test
    void register_addNullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                "User can't be null");
    }

    @Test
    void register_addNullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "User`s login can't be null");
    }

    @Test
    void register_addNullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "User`s age can't be null");
    }

    @Test
    void register_addNullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "User`s password can't be null");
    }

    @Test
    void validUser_Ok() {
        registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        Assertions.assertEquals(expected, user, "Age field's passed");
    }

    @Test
    void register_passwordLengthIsLessThanMinLength_notOk() {
        user.setPassword("321");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "User`s password should be 6 or more symbols");
    }

    @Test
    void register_passwordLengthIsLongerThanMinLength_ok() {
        user.setPassword(DEFAULT_PASSWORD + "1");
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual, "All user`s fields are correct");
    }

    @Test
    void register_loginExists_notOk() {
        registrationService.register(user);
        registrationService.register(user);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "User is already exists");
    }
}
