package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static final String DEFAULT_USER_LOGIN = "John";
    private static final String DEFAULT_USER_PASSWORD = "123456";
    private static final Integer DEFAULT_USER_AGE = 18;
    private User defaultTestUser;

    @BeforeAll
    public static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void init() {
        defaultTestUser = new User();
        defaultTestUser.setAge(DEFAULT_USER_AGE);
        defaultTestUser.setLogin(DEFAULT_USER_LOGIN);
        defaultTestUser.setPassword(DEFAULT_USER_PASSWORD);
    }

    @Test
    public void register_correctUser_ok() {
        User actualUser = service.register(defaultTestUser);
        assertEquals(defaultTestUser, actualUser);
    }

    @Test
    public void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> service.register(null));
    }

    @Test
    public void register_blankLogin_notOk() {
        defaultTestUser.setLogin(" ");
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_zeroLoginLength_notOk() {
        defaultTestUser.setLogin("");
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_blankPassword_notOk() {
        String password = " ".repeat(6);
        defaultTestUser.setPassword(password);
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_passwordContainBlanks_notOk() {
        String password = " 12345";
        defaultTestUser.setPassword(password);
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_nullAge_notOk() {
        defaultTestUser.setAge(null);
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_nullLogin_notOk() {
        defaultTestUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_nullPassword_notOk() {
        defaultTestUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_ageLessThanRequired_notOk() {
        defaultTestUser.setAge(17);
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_passwordLengthLessThanRequired_notOk() {
        defaultTestUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @Test
    public void register_userIsAlreadyRegistered_notOk() {
        Storage.people.add(defaultTestUser);
        assertThrows(RuntimeException.class, () -> service.register(defaultTestUser));
    }

    @AfterEach
    public void cleanUpStorage() {
        Storage.people.remove(defaultTestUser);
    }
}
