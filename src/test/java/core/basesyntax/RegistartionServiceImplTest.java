package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistartionServiceImplTest {

    private static final String PASSWORD_TEST = "absdefgh";
    private static final String LOGIN_TEST = "ThisIsMyLogin";
    private static final int AGE_TEST = 34;
    private static RegistrationService service;

    @BeforeAll
     static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_LoginNull_NotOk() {
        User user = new User(null,PASSWORD_TEST, AGE_TEST);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_PasswordNull_NotOk() {
        User user = new User(LOGIN_TEST,null, AGE_TEST);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_PasswordLengthLessThan6_notOk() {
        User userPaasowordCharZero = new User(LOGIN_TEST, "", AGE_TEST);
        User userPasswordCharThree = new User(LOGIN_TEST, "abc", AGE_TEST);
        User userPasswordCharFive = new User(LOGIN_TEST, "abcde", AGE_TEST);
        assertThrows(RegistrationException.class, () -> service.register(userPaasowordCharZero));
        assertThrows(RegistrationException.class, () -> service.register(userPasswordCharThree));
        assertThrows(RegistrationException.class, () -> service.register(userPasswordCharFive));
    }

    @Test
    void register_LoginLengthLessThan6_notOk() {
        User userLoginCharZero = new User("", PASSWORD_TEST, AGE_TEST);
        User userLoginCharThree = new User("Thi", PASSWORD_TEST, AGE_TEST);
        User userLogonCharFive = new User("ThisI", PASSWORD_TEST, AGE_TEST);
        assertThrows(RegistrationException.class, () -> service.register(userLoginCharZero));
        assertThrows(RegistrationException.class, () -> service.register(userLoginCharThree));
        assertThrows(RegistrationException.class, () -> service.register(userLogonCharFive));
    }

    @Test
    void register_LoginLengthGreaterThan6_Ok() {
        User userLoginCharEight = new User("ThisIsMy", PASSWORD_TEST, AGE_TEST);
        User userLoginCharFourteen = new User(LOGIN_TEST, PASSWORD_TEST, AGE_TEST);
        assertEquals(userLoginCharEight, service.register(userLoginCharEight));
        assertEquals(userLoginCharFourteen, service.register(userLoginCharFourteen));
    }

    @Test
    void register_PasswordLengthGreaterThan6_Ok() {
        User userPasswordCharEight = new User("ThisIsMyLoginTest", PASSWORD_TEST, AGE_TEST);
        User userPasswordCharFourteen = new User(LOGIN_TEST, "abcdefgh123456", AGE_TEST);
        assertEquals(userPasswordCharEight, service.register(userPasswordCharEight));
        assertEquals(userPasswordCharFourteen, service.register(userPasswordCharFourteen));
    }

    @Test
    void register_AgeLessThan18_notOk() {
        User actual = new User(LOGIN_TEST,PASSWORD_TEST, 14);
        assertThrows(RegistrationException.class, () -> service.register(actual));
    }

    @Test
    void register_AgeGreaterThan18_Ok() {
        User expected = new User(LOGIN_TEST,PASSWORD_TEST, AGE_TEST);
        User actual = service.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_UserAlreadyExist_notOk() {
        User user = new User("ThisIsMyLoginTheSame",PASSWORD_TEST, AGE_TEST);
        Storage.people.add(user);
        assertThrows(RegistrationException.class,
                () -> service.register(new User("ThisIsMyLoginTheSame",PASSWORD_TEST, AGE_TEST)));
    }

    @Test
    void register_StorageSizeAfterRegistration() {
        List<User> expected = new ArrayList<>();
        User user;
        for (int i = 0; i < 5; i++) {
            user = new User(LOGIN_TEST + i, PASSWORD_TEST, AGE_TEST);
            expected.add(user);
            service.register(user);
        }
        assertEquals(expected.size(), Storage.people.size());
    }

    @AfterEach
    void cleanStorage() {
        Storage.people.clear();
    }

}
