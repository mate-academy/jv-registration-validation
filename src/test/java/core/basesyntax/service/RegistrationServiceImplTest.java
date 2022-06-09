package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User("Login", "password", 21);
    }

    @Test
    public void registerUserNull_notOK() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void registerUserLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserLoginEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserPasswordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserLoginAndPasswordNull_NotOk() {
        user.setLogin(null);
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerAdultUser_ok() {
        user.setAge(18);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void registerYoungUser_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerPassword_ok() {
        user.setPassword("passwd");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void registerPassword_notOk() {
        user.setPassword("passw");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }


    @Test
    public void registerSameUser_notOk() {
        User user2 = new User(user.getLogin(), "anotherpassword", 56);
        registrationService.register(user2);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
