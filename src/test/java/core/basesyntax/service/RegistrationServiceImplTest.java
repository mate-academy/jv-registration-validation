package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "turtle54@gmail.com";
    private static final String DEFAULT_PASSWORD = "abcde12345";
    private static final int DEFAULT_AGE = 21;
    private User user;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    void register_userLoginExistsInStorage_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_registrationSuccess() {
        registrationService.register(user);
        assertEquals(1, Storage.people.size());
        assertEquals(user, Storage.people.get(0));
    }

    @Test
    void register_loginIsLessThanMinChars_notOk() {
        List<String> invalidLogins = Arrays.asList(null, "", " ", "abc", "abcde");
        for (String invalidLogin : invalidLogins) {
            user.setLogin(invalidLogin);
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    void register_passwordIsLessThanMinChars_notOk() {
        List<String> invalidPasswords = Arrays.asList(null, "", " ", "123", "12345");
        for (String invalidPassword : invalidPasswords) {
            user.setPassword(invalidPassword);
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    void register_ageIsLessThan18_notOk() {
        List<Integer> invalidAges = Arrays.asList(null, 0, 1, 5, 7, 9, 16, 17);
        for (Integer invalidAge : invalidAges) {
            user.setAge(invalidAge);
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    void register_ageNegativeNumber_notOk() {
        List<Integer> negativeAges = Arrays.asList(-1, -5, -100, -18, -21, -9);
        for (Integer negativeAge : negativeAges) {
            user.setAge(negativeAge);
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    void register_ageIs18_Ok() {
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
