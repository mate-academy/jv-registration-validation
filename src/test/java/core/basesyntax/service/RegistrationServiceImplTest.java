package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private static User equalUser;
    
    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        equalUser = new User();
    }

    @BeforeEach
    void setUp() {
        user.setId(21L);
        user.setLogin("bobius");
        user.setPassword("qwerty");
        user.setAge(23);

        equalUser.setId(21L);
        equalUser.setLogin("bobius");
        equalUser.setPassword("qwerty");
        equalUser.setAge(23);
    }

    @Test
    void register_ageLessThanMinimum_notOk() {
        user.setAge(12);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LackOfSymbolsInLogin_notOk() {
        user.setLogin("teddy");
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LackOfSymbolsInPassword_notOk() {
        user.setPassword("short");
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addExistingUser_notOk() {
        User register = registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(register));
    }

    @Test
    void checkEqualUsersByHashCode_Ok() {
        int userHashCode = user.hashCode();
        int equalUserHashCode = equalUser.hashCode();
        assertEquals(userHashCode, equalUserHashCode);
    }

    @Test
    void register_userExist_notOk() {
        Storage.people.add(equalUser);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
