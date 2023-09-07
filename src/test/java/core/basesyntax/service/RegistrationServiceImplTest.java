package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    public void init() {
        registrationService = new RegistrationServiceImpl();

        User user1 = new User();
        user1.setLogin("Bob123");
        user1.setPassword("Veryl0ngDick");
        user1.setAge(23);

        User user2 = new User();
        user2.setLogin("Alice35");
        user2.setPassword("tw1l1ght");
        user2.setAge(35);

        User user3 = new User();
        user3.setLogin("JohnCena");
        user3.setPassword("Wrestl1ng");
        user3.setAge(69);

        Storage.people.clear();

        Storage.people.add(user1);
        Storage.people.add(user2);
        Storage.people.add(user3);
    }

    @Test
    public void register_userNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_userExists_notOk() {
        User existingUser = new User();
        existingUser.setLogin("JohnCena");
        existingUser.setPassword("Wrestl1ng");
        existingUser.setAge(69);

        assertThrows(RegistrationException.class, () -> registrationService.register(existingUser));
    }

    @Test
    public void register_user_ok() {
        User newUser = new User();
        newUser.setLogin("newUser");
        newUser.setPassword("qwerty123");
        newUser.setAge(35);

        assertEquals(newUser, registrationService.register(newUser));
    }

    @Test
    public void register_login_notOk() {
        User invalidUser = new User();
        invalidUser.setLogin("user");
        invalidUser.setPassword("qwerty123");
        invalidUser.setAge(35);

        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setLogin("admin");
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));
    }

    @Test
    public void register_login_ok() {
        User validUser1 = new User();
        validUser1.setLogin("validU");
        validUser1.setPassword("Qwerty!@#");
        validUser1.setAge(69);

        assertEquals(validUser1, registrationService.register(validUser1));

        User validUser2 = new User();
        validUser2.setLogin("validUser2");
        validUser2.setPassword("Qwerty!@#");
        validUser2.setAge(69);

        assertEquals(validUser2, registrationService.register(validUser2));
    }

    @Test
    public void register_password_notOk() {
        User invalidUser = new User();
        invalidUser.setLogin("invalidUser");
        invalidUser.setPassword("Qwert");
        invalidUser.setAge(35);

        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setPassword("!@#");
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));
    }

    @Test
    public void register_password_ok() {
        User validUser1 = new User();
        validUser1.setLogin("validUser1");
        validUser1.setPassword("Qwerty");
        validUser1.setAge(69);

        assertEquals(validUser1, registrationService.register(validUser1));

        User validUser2 = new User();
        validUser2.setLogin("validUser2");
        validUser2.setPassword("Qwerty!@#");
        validUser2.setAge(69);

        assertEquals(validUser2, registrationService.register(validUser2));
    }

    @Test
    public void register_age_notOk() {
        User invalidUser = new User();
        invalidUser.setLogin("invalidUser");
        invalidUser.setPassword("Qwerty!@#");
        invalidUser.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setAge(-6);
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));

        invalidUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(invalidUser));
    }

    @Test
    public void register_age_ok() {
        User validUser1 = new User();
        validUser1.setLogin("validUser1");
        validUser1.setPassword("Qwerty!@#");
        validUser1.setAge(18);

        assertEquals(validUser1, registrationService.register(validUser1));

        User validUser2 = new User();
        validUser2.setLogin("validUser2");
        validUser2.setPassword("Qwerty!@#");
        validUser2.setAge(69);

        assertEquals(validUser2, registrationService.register(validUser2));
    }
}