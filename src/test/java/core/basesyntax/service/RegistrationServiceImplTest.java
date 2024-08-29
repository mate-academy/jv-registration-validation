package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
        user = new User();
        user.setLogin("Example1");
        user.setPassword("Password1");
        user.setAge(30);
        Storage.people.add(user);
    }

    @Test
    void registerExistingLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("Example1");
        newUser.setPassword("DifferentPass");
        newUser.setAge(25);
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerRepeatedLogin_Ok() {
        User newUser = new User();
        newUser.setLogin("PashaPasha");
        newUser.setPassword("ProperPassword2");
        newUser.setAge(28);
        User registeredUser = registrationService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals("PashaPasha", registeredUser.getLogin());
        assertEquals("ProperPassword2", registeredUser.getPassword());
        assertEquals(Integer.valueOf(28), registeredUser.getAge());
    }

    @Test
    void registerPasswordTooShort_NotOk() {
        User newUser = new User();
        newUser.setLogin("someLogin1");
        newUser.setPassword("short");
        newUser.setAge(28);
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerValidPassword_Ok() {
        User newUser = new User();
        newUser.setLogin("someLogin2");
        newUser.setPassword("Valid12345");
        newUser.setAge(28);
        User registeredUser = registrationService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals("someLogin2", registeredUser.getLogin());
        assertEquals("Valid12345", registeredUser.getPassword());
        assertEquals(Integer.valueOf(28), registeredUser.getAge());
    }

    @Test
    void registerAgeTooYoung_NotOk() {
        User newUser = new User();
        newUser.setLogin("YetAnotherUniqueLogin");
        newUser.setPassword("ValidPassword123");
        newUser.setAge(17);
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerValidAge_Ok() {
        User newUser = new User();
        newUser.setLogin("YetAnotherUniqueLogin1");
        newUser.setPassword("ValidPassword123");
        newUser.setAge(18);
        User registeredUser = registrationService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals("YetAnotherUniqueLogin1", registeredUser.getLogin());
        assertEquals("ValidPassword123", registeredUser.getPassword());
        assertEquals(Integer.valueOf(18), registeredUser.getAge());
    }

    @Test
    void registerTooShortLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("Pasha");
        newUser.setPassword("ValidPassword");
        newUser.setAge(28);
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerValidLogin_Ok() {
        User newUser = new User();
        newUser.setLogin("LongEnoughLogin");
        newUser.setPassword("ValidPassword123");
        newUser.setAge(28);
        User registeredUser = registrationService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals("LongEnoughLogin", registeredUser.getLogin());
        assertEquals("ValidPassword123", registeredUser.getPassword());
        assertEquals(Integer.valueOf(28), registeredUser.getAge());
    }

    @Test
    void registerNullUser_NotOk() {
        User nullUser = null;
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(nullUser));
    }

    @Test
    void registerLoginContainWhiteSpace_NotOk() {
        User newUser = new User();
        newUser.setLogin("Pasha Otroda");
        newUser.setPassword("ValidPassword");
        newUser.setAge(28);
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerTooLongPassword_Ok() {
        User newUser = new User();
        newUser.setLogin("somelogin");
        newUser.setPassword("ProperPassword123ProperPassword123ProperPassword123");
        newUser.setAge(28);
        User registeredUser = registrationService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals("somelogin", registeredUser.getLogin());
        assertEquals("ProperPassword123ProperPassword123ProperPassword123",
                registeredUser.getPassword());
        assertEquals(Integer.valueOf(28), registeredUser.getAge());
    }

    @Test
    void registerNegativeAge_NotOk() {
        User newUser = new User();
        newUser.setLogin("PashaOtroda");
        newUser.setPassword("ValidPassword");
        newUser.setAge(-28);
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(newUser));
    }
}
