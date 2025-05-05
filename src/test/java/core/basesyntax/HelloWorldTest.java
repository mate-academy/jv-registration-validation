package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static final int MIN_AGE = 18;
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "user_login@gmail.com";
    private static User user;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void initDefaultUser() {
        user = new User();
        user.setAge(MIN_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_addAdultUser_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual,"User is added to Storage");
    }

    @Test
    void register_userRegistered_notOk() {
        registrationService.register(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "User already exists");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Login can't be null");
    }

    @Test
    void register_lessAge_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Not valid age");
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-57);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Age can't be negative");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Age can't be null");
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("vika");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Not valid password");
    }

    @Test
    void register_password_ok() {
        user.setPassword("viktoriya");
        User actual = registrationService.register(user);
        assertEquals(user,actual,"User add in Storage");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                "Password can't be null");
    }
}
