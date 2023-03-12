package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static User userToCheck;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void classImplementation() {
        userToCheck = new User();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearingListOfUsers() {
        Storage.people.clear();
    }

    @BeforeEach
    void fillingListWith_1_ValidUser() {
        userToCheck.setLogin("login");
        userToCheck.setAge(20);
        userToCheck.setId(4324L);
        userToCheck.setPassword("password");
    }

    @Test
    void register_allValidUsers_Ok() {
        assertEquals(registrationService.register(userToCheck), userToCheck);
        User userToCheck2 = creatingDefaultUser();
        assertEquals(registrationService.register(userToCheck2), userToCheck2);
    }

    @Test
    void register_passwordIsNullOrLess_6_characters() {
        userToCheck.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        userToCheck.setPassword("null");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        userToCheck.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        userToCheck.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        userToCheck.setPassword("m");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
    }

    @Test
    void register_AgeLessThan_18_notOk() {
        userToCheck.setAge(1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        userToCheck.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        userToCheck.setAge(12);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
    }

    @Test
    void register_NullAgeUser_notOk() {
        userToCheck.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
    }

    @Test
    void register_negativeAgeUser_notOk() {
        userToCheck.setAge(-43);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        userToCheck.setAge(-1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = new User();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_loginIsNotNullAndNotEmpty_Check() {
        userToCheck.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        userToCheck.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
    }

    @Test
    void register_sameLogin_notOk() {
        userToCheck.setLogin("login12344546");
        assertEquals(registrationService.register(userToCheck), userToCheck);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck);
        });
        User userToCheck2 = creatingDefaultUser();
        userToCheck2.setLogin("5235L");
        assertEquals(registrationService.register(userToCheck2), userToCheck2);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userToCheck2);
        });
    }

    private User creatingDefaultUser() {
        User userToReturn = new User();
        userToReturn.setLogin("bestLOgin");
        userToReturn.setAge(20);
        userToReturn.setId(4324L);
        userToReturn.setPassword("password");
        return userToReturn;
    }
}
