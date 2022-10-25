package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int ILLEGAL_AGE = 12;
    private static final int LEGAL_AGE = 22;
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
        Storage.people.clear();
        user = new User();
        user.setLogin(LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(LEGAL_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            registrationService.register(user); });
        assertEquals("User can’t be null", thrown.getMessage());
    }

    @Test
    void register_returnUser_Ok() {
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_loginIsAlreadyExist_notOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_invalidPassword_notOK() {
        user.setPassword(ILLEGAL_PASSWORD_LENGTH);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_ageBelow18_notOk() {
        user.setAge(ILLEGAL_AGE);
        assertThrows(RuntimeException.class,() -> {
            registrationService.register(user); });
    }

    @Test
    void register_getUser_ok() {
        storageDao.add(user);
        User expected = user;
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected,actual, "Cant find user by this login");
    }
}


