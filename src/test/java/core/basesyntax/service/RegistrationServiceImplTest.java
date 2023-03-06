package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    public static final int VALID_AGE = 18;
    public static final int LOWER_18 = 17;
    public static final String VALID_LOGIN = "login";
    public static final String VALID_PASSWORD = "password";
    public static final String WRONG_PASSWORD = "wrong";
    private static RegistrationServiceImpl service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_NullUser_false() {
        assertThrows(InvalidUserDataException.class, () ->
                service.register(null), " expected "
                + InvalidUserDataException.class.getName()
                + " to be thrown for less length login "
                + user
                + " but it wasn't");
    }

    @Test
    void register_NullLogin_false() {
        user.setLogin(null);
        assertThrows(InvalidUserDataException.class, () ->
                service.register(user)," expected "
                + InvalidUserDataException.class.getName()
                + " to be thrown for less length login "
                + user.getLogin()
                + " but it wasn't");
    }

    @Test
    void register_NullAge_false() {
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () ->
                service.register(user)," expected "
                + InvalidUserDataException.class.getName()
                + " to be thrown for null age "
                + user.getAge()
                + " but it wasn't");
    }

    @Test
    void register_NullPass_false() {
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () ->
                service.register(user)," expected "
                + InvalidUserDataException.class.getName()
                + " to be thrown for null password "
                + user.getPassword()
                + " but it wasn't");
    }

    @Test
    void register_loginDublicate_false() {
        Storage.people.add(user);
        User newUser = new User(user.getLogin(), VALID_PASSWORD, VALID_AGE);
        assertThrows(InvalidUserDataException.class, () -> service.register(newUser),
                "This login already exists");
    }

    @Test
    void register_passwordShort_false() {
        user.setPassword(WRONG_PASSWORD);
        assertThrows(InvalidUserDataException.class, () -> service.register(user),
                " expected " + InvalidUserDataException.class.getName()
                    + " to be thrown for age " + user.getPassword() + " but it wasn't");
    }

    @Test
    void register_userAgeUnder18_false() {
        user.setAge(LOWER_18);
        assertThrows(InvalidUserDataException.class, () -> service.register(user),
                " expected " + InvalidUserDataException.class.getName()
                    + " to be thrown for less age "
                    + user.getAge()
                    + " but it wasn't");
    }

    @Test
    void register_userValid_true() {
        assertDoesNotThrow(() -> service.register(user));
    }

}
