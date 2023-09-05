package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "ValidLogin";
    private static final String VALID_PASSWORD = "12345678";
    private static final int VALID_AGE = 20;

    private static RegistrationServiceImpl regService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        regService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_allParamsValid_ok() {
        User actual = regService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nllUser_throwsException() {
        user = null;
        assertThrows(NullPointerException.class, () -> regService.register(user));
    }

    @Test
    void register_nllLogin_throwsException() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> regService.register(user));
    }

    @Test
    void register_nllAge_throwsException() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> regService.register(user));
    }

    @Test
    void register_nllPassword_throwsException() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> regService.register(user));
    }

    @Test
    void register_invalidLogin_throwsException() {
        user.setLogin("a");
        assertThrows(RegisterException.class, () -> regService.register(user));
    }

    @Test
    void register_invalidAge_throwsException() {
        user.setAge(12);
        assertThrows(RegisterException.class, () -> regService.register(user));
    }

    @Test
    void register_invalidPassword_throwsException() {
        user.setPassword("1234");
        assertThrows(RegisterException.class, () -> regService.register(user));
    }

    @Test
    void register_userAlreadyExist_throwsException() {
        regService.register(user);
        assertThrows(RegisterException.class, () -> regService.register(user));
    }
}
