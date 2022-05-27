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
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testUser");
        user.setAge(23);
        user.setPassword("goodPassword");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_blankLogin_notOk() {
        user.setLogin("            ");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_blankPassword_notOk() {
        user.setPassword("         ");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-10);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(12);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("bad");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_existingUser_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_uniqueUser_Ok() {
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_suitableAge_Ok() {
        user.setAge(18);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_aboveAge_Ok() {
        user.setAge(34);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_suitablePassword_Ok() {
        user.setPassword("whyNot");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_longPassword_OK() {
        user.setPassword("TheBestPasswordEver123");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }
}
