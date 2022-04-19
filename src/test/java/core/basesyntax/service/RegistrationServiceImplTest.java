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
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(18);
        user.setLogin("defaultUser");
        user.setPassword("defaultPassword");
    }

    @Test
    void registration_validValues_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void registration_nullUser_ThrowsException() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void registration_withNullLogin_ThrowsException() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_under18_ThrowsException() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_negativeAge_ThrowsException() {
        user.setAge(-18);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_older18_ok() {
        user.setAge(22);
        User actual = registrationService.register(user);
        assertEquals(user.getAge(), actual.getAge());
    }

    @Test
    void registration_nullAge_ThrowsException() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_passwordLessThanSixCharacters_ThrowsException() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_passwordIsNull_ThrowsException() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_withExcitingLogin_ThrowsException() {
        User bob = new User();
        bob.setLogin("bob");
        bob.setAge(20);
        bob.setPassword("bobpassword");
        registrationService.register(bob);
        User otherBob = new User();
        otherBob.setLogin("bob");
        otherBob.setAge(20);
        otherBob.setPassword("otherbobpassword");
        assertThrows(RuntimeException.class, () -> registrationService.register(otherBob));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
