package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int ILLEGAL_AGE = 12;
    private static final int LEGAL_AGE = 22;
    private static final long EXPECTED_INDEX = 1L;
    private static final String ILLEGAL_PASSWORD_LENGTH = "Pass";
    private static final String VALID_PASSWORD = "Password";
    private static final String LOGIN = "Login";

    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(LEGAL_AGE);
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_LoginIsAlreadyExist_NotOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_PasswordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_PasswordLessThanSix() {
        user.setPassword(ILLEGAL_PASSWORD_LENGTH);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_AgeIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_AgeBelow18_NotOk() {
        user.setAge(ILLEGAL_AGE);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_UserIsAddAndGet_Ok() {
        storageDao.add(user);
        User expected = user;
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected,actual, "Cant find user by this login");
        long expectedIndex = EXPECTED_INDEX;
        long actualIndex = storageDao.get(user.getLogin()).getId();
        assertEquals(expectedIndex, actualIndex, "Actual size of list is "
                + actual + ". Expected size is " + expected);
    }

    @Test
    void strorageReturn_OK() {
        user.setLogin("NewLogin");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected,actual, "Cant find user by this login");
    }
}


