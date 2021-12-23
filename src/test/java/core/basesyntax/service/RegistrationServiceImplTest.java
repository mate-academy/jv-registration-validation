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
    private static User user;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("MyNickname@mateAcademy");
        user.setPassword("needMore6Numbers");
        user.setAge(18);
    }

    @Test
    void createUser_notNull_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void createUser_null_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegistration_isLoginNull_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegistration_LoginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegistration_LoginIsBlank_notOk() {
        user.setLogin(" ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegistration_duplicateLogin_notOk() {
        registrationService.register(user);
        User userDuplicate = new User();
        userDuplicate.setLogin(user.getLogin());
        userDuplicate.setPassword(user.getPassword());
        userDuplicate.setAge(user.getAge());
        assertThrows(RuntimeException.class, () -> registrationService.register(userDuplicate));
    }

    @Test
    void userRegistration_ageNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegistration_ageLess18_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegistration_PasswordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegistration_PasswordLess6_NotOk() {
        user.setPassword("less");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegistration_PasswordIsBlank_notOk() {
        user.setPassword("       ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
