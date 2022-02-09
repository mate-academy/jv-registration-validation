package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user = new User();

    @BeforeEach
    public void setUp() {
        user.setLogin("user123");
        user.setAge(18);
        user.setPassword("123456");
    }

    @AfterEach
    public void cleanDataBase() {
        Storage.people.clear();
    }

    @Test
    public void register_User_ok() {
        User excepted = user;
        User testUser = registrationService.register(excepted);
        assertEquals(excepted, testUser, "Please enter another login");
    }

    @Test
    public void register_userWithNullValueOfLogin_notOk() {
        User testUser = user;
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    public void register_userWithEmptyValueOfLogin_notOk() {
        User testUser = user;
        testUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    public void register_userWithNullValue_notOk() {
        User excepted = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userBadPassword_notOk() {
        User testUser = user;
        testUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User testUser = user;
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userBadAge_notOk() {
        User testUser = user;
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userWithNullAgeValue_notOk() {
        User testUser = user;
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    public void register_userLoginAlreadyExit_notOk() {
        Storage.people.add(user);
        User testUser = new User();
        testUser.setLogin(user.getLogin());
        testUser.setPassword("123456");
        testUser.setAge(18);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUser));
    }
}
