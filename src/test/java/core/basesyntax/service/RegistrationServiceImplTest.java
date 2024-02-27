package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(ValidationException.class, () ->
                service.register(null));
    }

    @Test
    void register_sameLogin_notOk() {
        user.setLogin("Max1999");
        user.setPassword("123456");
        Storage.people.add(user);
        User newUser = new User();
        newUser.setLogin("Max1999");
        assertThrows(ValidationException.class, () ->
                service.register(newUser));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void register_lessThanSixLoginLength_notOk() {
        user.setLogin("1");
        assertThrows(ValidationException.class, () ->
                service.register(user));
        user.setLogin("w42");
        assertThrows(ValidationException.class, () ->
                service.register(user));
        user.setLogin("u125R");
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void register_equalsOrMoreThanSixLoginLength_ok() {
        user.setLogin("Eva123");
        user.setPassword("123456");
        Storage.people.add(user);
        assertTrue(Storage.people.contains(user));
        Storage.people.clear();
        user.setLogin("01Eva123");
        Storage.people.add(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("123456ID");
        user.setPassword(null);
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void register_lessThanSixPasswordLength_notOk() {
        user.setLogin("12tr3456");
        user.setPassword("12");
        assertThrows(ValidationException.class, () ->
                service.register(user));
        Storage.people.clear();
        user.setLogin("6372wv34");
        user.setPassword("123");
        assertThrows(ValidationException.class, () ->
                service.register(user));
        Storage.people.clear();
        user.setLogin("836128de");
        user.setPassword("12345");
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void register_equalsOrMoreThanSixPasswordLength_ok() {
        user.setLogin("Make123");
        user.setPassword("012345");
        Storage.people.add(user);
        assertTrue(Storage.people.contains(user));
        Storage.people.clear();
        user.setLogin("01Dana123");
        user.setPassword("012345");
        Storage.people.add(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void service_nullAge_notOk() {
        user.setLogin("12Make123");
        user.setPassword("ds012345");
        user.setAge(null);
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void service_AgeLessThanEighteenth_notOk() {
        user.setLogin("1ab3426");
        user.setPassword("423457");
        user.setAge(-20);
        assertThrows(ValidationException.class, () ->
                service.register(user));
        Storage.people.clear();
        user.setLogin("1272ew53");
        user.setPassword("12345423");
        user.setAge(0);
        assertThrows(ValidationException.class, () ->
                service.register(user));
        Storage.people.clear();
        user.setLogin("8wa36128");
        user.setPassword("44434512");
        user.setAge(11);
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void service_AgeEqualsOrMoreThanEighteenth_ok() {
        user.setLogin("Olly563");
        user.setPassword("12312345");
        user.setAge(18);
        Storage.people.add(user);
        assertTrue(Storage.people.contains(user));
        Storage.people.clear();
        user.setLogin("1022Gaga3");
        user.setPassword("1284345");
        user.setAge(45);
        Storage.people.add(user);
        assertTrue(Storage.people.contains(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
