package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StorageDaoTests {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setAge(18);
        user.setLogin("mateAcademy");
        user.setPassword("mateAcademy2021");
    }
    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_alreadyContainingUser_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user), "This user already exists!");
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(null), "User might not be null!");
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Login might not be null!");
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Login might not be empty!");
    }

    @Test
    void  register_emptyUser_NotOk() {
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(new User()), "Please write anything!");
    }

    @Test
    void register_IncorrectLogin_notOk() {
        user.setLogin("@hojomojo$$1");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Login is incorrect");
    }

    @Test
    void register_PassEqualLogin_notOk() {
        user.setPassword("mateAcademy");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Password might not be equals Login");
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Age might not be null!");
    }

    @Test
    void register_LessThenZeroAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Age might not less then ZERO!");
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Age might be 18+!");
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Password might not be null!");
    }

    @Test
    void register_lessThanNeedPassword_NotOk() {
        user.setPassword("#$133");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user), "Password might be 6+!");
    }
}
