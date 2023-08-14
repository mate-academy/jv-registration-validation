package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private List<User> users;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        
        User validUser = new User(45534554L, "userNumberOne", "123412346", 18);
        
        User smallLoginUser = new User(4553712354L, "user2", "456456754", 19);

        User smallPasswordUser = new User(12547646854L, "userNumberThree", "34568", 20);

        User smallAgeUser = new User(2357568L, "userNumberFour", "345645745684", 16);

        User nullLoginUser = new User(456356734L, null, "3465346345", 31);

        User nullPasswordUser = new User(456356734L, "nullPasswordUser", null, 31);

        User recurringUser = new User(34534573L, "recurringUser", "235357457", 20);
        
        users.add(validUser);
        users.add(smallLoginUser);
        users.add(smallPasswordUser);
        users.add(smallAgeUser);
        users.add(nullLoginUser);
        users.add(nullPasswordUser);
        users.add(recurringUser);
    }

    @Test
    void registerSuccessful_Ok() {
        User actual = users.get(0);
        assertEquals(actual, registrationService.register(actual));
    }

    @Test
    void storageContainsUser_notOk() {
        registrationService.register(users.get(6));
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(6));
        });
    }

    @Test
    void loginLessThanSixChars_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(1));
        });
    }

    @Test
    void passwordLessThanSixChars_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(2));
        });
    }

    @Test
    void ageLessThanEighteen_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(3));
        });
    }

    @Test
    void nullLogin_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(4));
        });
    }

    @Test
    void nullPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(5));
        });
    }
}
