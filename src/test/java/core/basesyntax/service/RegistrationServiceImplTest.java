package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final User DEFAULT_VALID_USER = new User();
    private static final User SECOND_VALID_USER = new User();
    private static final User THIRD_VALID_USER = new User();

    private static RegistrationServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        DEFAULT_VALID_USER.setAge(20);
        DEFAULT_VALID_USER.setPassword("DefaultPassword");
        DEFAULT_VALID_USER.setLogin("DefaultLogin");
        DEFAULT_VALID_USER.setId(null);
        SECOND_VALID_USER.setAge(20);
        SECOND_VALID_USER.setPassword("Password123");
        SECOND_VALID_USER.setLogin("Login123");
        SECOND_VALID_USER.setId(null);
        THIRD_VALID_USER.setAge(21);
        THIRD_VALID_USER.setPassword("JackIsCool");
        THIRD_VALID_USER.setLogin("JackIsCool");
        THIRD_VALID_USER.setId(null);
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        DEFAULT_VALID_USER.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_invalidAge_notOk() {
        DEFAULT_VALID_USER.setAge(-13);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setAge(0);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setAge(17);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_validAge_Ok() {
        DEFAULT_VALID_USER.setAge(18);
        assertEquals(DEFAULT_VALID_USER, service.register(DEFAULT_VALID_USER));
        SECOND_VALID_USER.setAge(23);
        assertEquals(SECOND_VALID_USER, service.register(SECOND_VALID_USER));
    }

    @Test
    void register_nullLogin_notOk() {
        DEFAULT_VALID_USER.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_invalidLogin_notOk() {
        String firstLogin = "jack";
        String secondLogin = "";
        String thirdLogin = "jack1";

        DEFAULT_VALID_USER.setLogin(firstLogin);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setLogin(secondLogin);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setLogin(thirdLogin);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_validLogin_Ok() {
        String firstLogin = "jack123";
        String secondLogin = "Jacob1";
        DEFAULT_VALID_USER.setLogin(firstLogin);
        assertEquals(DEFAULT_VALID_USER, service.register(DEFAULT_VALID_USER));
        SECOND_VALID_USER.setLogin(secondLogin);
        assertEquals(SECOND_VALID_USER, service.register(SECOND_VALID_USER));
    }

    @Test
    void register_nullPassword_notOk() {
        DEFAULT_VALID_USER.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_invalidPassword_notOk() {
        String firstPassword = "jack";
        String secondPassword = "";
        String thirdPassword = "jack1";

        DEFAULT_VALID_USER.setPassword(firstPassword);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setPassword(secondPassword);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setPassword(thirdPassword);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_validPassword_Ok() {
        String firstPassword = "jack123";
        String secondPassword = "Jacob1";
        DEFAULT_VALID_USER.setPassword(firstPassword);
        assertEquals(DEFAULT_VALID_USER, service.register(DEFAULT_VALID_USER));
        SECOND_VALID_USER.setPassword(secondPassword);
        assertEquals(SECOND_VALID_USER, service.register(SECOND_VALID_USER));
    }

    @Test
    void register_DuplicateLogin_notOk() {
        Storage.people.add(DEFAULT_VALID_USER);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }
}