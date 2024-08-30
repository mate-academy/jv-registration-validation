package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {

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
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerPasswordTooShort_NotOk() {
        User newUser = new User();
        newUser.setLogin("someLogin1");
        newUser.setPassword("short");
        newUser.setAge(28);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerValidUser_Ok() {
        User newUser = new User();
        newUser.setLogin("someLogin2");
        newUser.setPassword("Valid12345");
        newUser.setAge(28);
        User registeredUser = registrationService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals(newUser.getLogin(), registeredUser.getLogin());
        assertEquals(newUser.getPassword(), registeredUser.getPassword());
        assertEquals(newUser.getAge(), registeredUser.getAge());
    }

    @Test
    void registerAgeTooYoung_NotOk() {
        User newUser = new User();
        newUser.setLogin("YetAnotherUniqueLogin");
        newUser.setPassword("ValidPassword123");
        newUser.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerTooShortLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("Pasha");
        newUser.setPassword("ValidPassword");
        newUser.setAge(28);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void registerLoginContainWhiteSpace_NotOk() {
        User newUser = new User();
        newUser.setLogin("Pasha Otroda");
        newUser.setPassword("ValidPassword");
        newUser.setAge(28);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin(null);
        newUser.setPassword("ValidPassword");
        newUser.setAge(28);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullPassword_NotOk() {
        User newUser = new User();
        newUser.setLogin("someLogin1");
        newUser.setPassword(null);
        newUser.setAge(28);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullAge_NotOk() {
        User newUser = new User();
        newUser.setLogin("PashaOtroda");
        newUser.setPassword("ValidPassword");
        newUser.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNegativeAge_NotOk() {
        User newUser = new User();
        newUser.setLogin("PashaOtroda");
        newUser.setPassword("ValidPassword");
        newUser.setAge(-28);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }
}
