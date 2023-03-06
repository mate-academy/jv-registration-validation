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
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_NullUser_false() {
        assertThrows(InvalidUserDataException.class, () ->
                service.register(null), "User can't be null");
    }

    @Test
    void register_NullLogin_false() {
        user.setLogin(null);
        assertThrows(InvalidUserDataException.class, () ->
                service.register(user),"Login can't be null");
    }

    @Test
    void register_NullAge_false() {
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () ->
                service.register(user),"Age can't be null");
    }

    @Test
    void register_NullPass_false() {
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () ->
                service.register(user),"Password can't be null");
    }

    @Test
    void register_loginDublicate_false() {
        Storage.people.add(user);
        User newUser = new User();
        newUser.setPassword(VALID_PASSWORD);
        newUser.setAge(VALID_AGE);
        newUser.setLogin(user.getLogin());
        assertThrows(InvalidUserDataException.class, () -> service.register(newUser),
                "This login already exists");
    }

    @Test
    void register_passwordShort_false() {
        user.setPassword(WRONG_PASSWORD);
        assertThrows(InvalidUserDataException.class, () -> service.register(user),
                "Password must contains at least 6 characters");
    }

    @Test
    void register_userAgeUnder18_notOk() {
        user.setAge(LOWER_18);
        assertThrows(InvalidUserDataException.class, () -> service.register(user),
                "Age of user is invalid.");
    }

    @Test
    void register_userAgeIsNegative_notOk() {
        user.setAge(VALID_AGE * -1);
        assertThrows(InvalidUserDataException.class, () -> service.register(user),
                "Age of user can`t be negative");
    }

    @Test
    void register_userValid_true() {
        assertDoesNotThrow(() -> service.register(user),
                "User is added to storage");
    }

}
