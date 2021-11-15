package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User expectedUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        expectedUser = new User();
    }

    @BeforeEach
    public void setUp() {
        expectedUser.setLogin("login");
        expectedUser.setPassword("password");
        expectedUser.setAge(18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        User newUser = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullLogin_notOk() {
        expectedUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    public void register_nullAge_notOk() {
        expectedUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_nullPassword_notOk() {
        expectedUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_existedUser_notOk() {
        User newUser = registrationService.register(expectedUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_notExistedUser_Ok() {
        assertEquals(expectedUser,registrationService.register(expectedUser));
    }

    @Test
    void register_notValidAge_notOk() {
        expectedUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_notValidPassword_notOk() {
        expectedUser.setPassword("qweqq");
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser));

    }

    @Test
    void register_validAge_Ok() {
        assertTrue(expectedUser.getAge() >= 18);
    }

    @Test
    void register_validPassword_Ok() {
        assertTrue(expectedUser.getPassword().length() >= 6);

    }
}
