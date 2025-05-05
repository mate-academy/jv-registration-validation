package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_PASS = "more_than_six";
    private static final String INVALID_PASS = "four";
    private static final int VALID_AGE = 25;
    private static final int INVALID_AGE = 5;
    private static User defaultUser;
    private static User anotherUser;
    private static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        defaultUser = new User("Mate", VALID_PASS, VALID_AGE);
        anotherUser = new User("Academic", VALID_PASS, VALID_AGE);
    }

    @BeforeEach
    void setUp() {
        defaultUser.setLogin("Mate");
        defaultUser.setAge(VALID_AGE);
        defaultUser.setPassword(VALID_PASS);
        anotherUser.setLogin("Academic");
        anotherUser.setAge(VALID_AGE);
        anotherUser.setPassword(VALID_PASS);
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> service.register(defaultUser), "");
    }

    @Test
    void register_lowAge_notOk() {
        defaultUser.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class, () -> service.register(defaultUser));
        defaultUser.setAge(Integer.MIN_VALUE);
        assertThrows(RuntimeException.class, () -> service.register(defaultUser));
    }

    @Test
    void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> service.register(defaultUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        defaultUser.setLogin("");
        assertThrows(RuntimeException.class, () -> service.register(defaultUser));
    }

    @Test
    void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> service.register(defaultUser));
    }

    @Test
    void register_shortPassword_notOk() {
        defaultUser.setPassword("");
        assertThrows(RuntimeException.class, () -> service.register(defaultUser));
        defaultUser.setPassword(INVALID_PASS);
        assertThrows(RuntimeException.class, () -> service.register(defaultUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> service.register(null));
    }

    @Test
    void notEqualsUsers_notOk() {
        assertNotEquals(defaultUser, anotherUser);
        assertNotEquals(null, defaultUser);
    }

    @Test
    void equalsUsers_ok() {
        anotherUser.setLogin(defaultUser.getLogin());
        anotherUser.setAge(defaultUser.getAge());
        anotherUser.setPassword(defaultUser.getPassword());
        assertEquals(defaultUser, anotherUser);
    }

    @Test
    void register_addUser_ok() {
        assertEquals(service.register(defaultUser), defaultUser);
    }

    @Test
    void register_notNullId_ok() {
        assertNotEquals(null, service.register(defaultUser).getId());
    }

    @Test
    void register_addSameLoginUser_notOk() {
        service.register(defaultUser);
        anotherUser.setLogin(defaultUser.getLogin());
        assertThrows(RuntimeException.class, () -> service.register(anotherUser));
    }
}
