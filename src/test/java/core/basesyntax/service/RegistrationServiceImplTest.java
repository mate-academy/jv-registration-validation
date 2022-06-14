package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "Emma";
    private static final String VALID_PASSWORD = "qa#wd4f_tp";
    private static final int VALID_AGE = 20;
    private static final int MAX_INVALID_AGE = 17;
    private static RegistrationServiceImpl service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_validUser_Ok() {
        User actual = service.register(user);
        assertEquals(user, actual);
        assertTrue(Storage.people.contains(user));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_existedUser_notOk() {
        Storage.people.add(user);
        User sameUser = new User();
        sameUser.setLogin(VALID_LOGIN);
        sameUser.setPassword(VALID_PASSWORD);
        sameUser.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> service.register(user));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_tooYoungUser_notOk() {
        user.setAge(MAX_INVALID_AGE);
        assertThrows(RuntimeException.class, () -> service.register(user));
        assertFalse(Storage.people.contains(user));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("Q&");
        assertThrows(RuntimeException.class, () -> service.register(user));
        assertFalse(Storage.people.contains(user));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> service.register(null));
        assertFalse(Storage.people.contains(null));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> service.register(user));
        assertFalse(Storage.people.contains(user));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> service.register(user));
        assertFalse(Storage.people.contains(user));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_emptyLogin_Ok() {
        user.setLogin("");
        User actual = service.register(user);
        assertEquals(user, actual);
        assertTrue(Storage.people.contains(user));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> service.register(user));
        assertFalse(Storage.people.contains(user));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_ageExceededMaxValue_notOk() {
        user.setAge(Integer.MAX_VALUE + 1);
        assertThrows(RuntimeException.class, () -> service.register(user));
        assertFalse(Storage.people.contains(user));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-19);
        assertThrows(RuntimeException.class, () -> service.register(user));
        assertFalse(Storage.people.contains(user));
        assertEquals(0, Storage.people.size());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
