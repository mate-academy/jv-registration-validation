package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void userWithShortLogin_NotOk() {
        user.setLogin("sui");
        user.setPassword("123");
        user.setAge(22);
        assertThrows(RegisterExtension.class, () -> registrationService.register(user));
    }

    @Test
    void userWithShortPassword_NotOk() {
        user.setLogin("misha123@gmail.com");
        user.setPassword("193");
        user.setAge(23);
        assertThrows(RegisterExtension.class, () -> registrationService.register(user));
    }

    @Test
    void userWithCorrectAge_NotOk() {
        user.setLogin("misha123@gmail.com");
        user.setPassword("MiSha123");
        user.setAge(15);
        assertThrows(RegisterExtension.class, () -> registrationService.register(user));
    }

    @Test
    void containcsGmailDomen_Ok() {
        user.setLogin("misha123@gmail.com");
        user.setPassword("MiSha123");
        user.setAge(23);
        User register = registrationService.register(user);
        assertEquals(user.getLogin(), register.getLogin());
    }

    @Test
    void nullValueUser_NotOk() {
        assertThrows(RegisterExtension.class, () -> registrationService.register(null));
    }

    @Test
    void emailWithNull_NotOk() {
        user.setLogin(null);
        user.setPassword("Mi123");
        user.setAge(18);
        assertThrows(RegisterExtension.class, () -> registrationService.register(user));
    }

    @Test
    void passwordWithNull_NotOk() {
        user.setLogin("misha123@gmail.com");
        user.setPassword(null);
        user.setAge(37);
        assertThrows(RegisterExtension.class, () -> registrationService.register(user));
    }

    @Test
    void ageWithNull_NotOk() {
        user.setLogin("misha123@gmail.com");
        user.setPassword("MiSha123");
        user.setAge(null);
        assertThrows(RegisterExtension.class, () -> registrationService.register(user));
    }

    @Test
    void userAlreadyRegister_NotOk() {
        User user1 = new User();
        user1.setLogin("misha123@gmail.com");

        Storage.people.add(user1);

        User user2 = new User();
        user2.setLogin("misha123@gmail.com");

        assertThrows(RegisterExtension.class, () -> registrationService.register(user2));

    }
}
