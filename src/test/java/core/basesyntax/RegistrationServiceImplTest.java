package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegisterFailedException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final User user = new User("Bob_Super", "Bob12345", 18);

    private static RegistrationServiceImpl service;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao.add(user);
    }

    @Test
    void register_validUser_Ok() {
        User validNewUser = new User("_Emily_", "_emily_", 22);
        User validUserResult = service.register(validNewUser);
        User validEdgeNewUser = new User("_Emily", "emily_", 18);
        User validEdgeLoginResult = service.register(validEdgeNewUser);

        assertEquals(validUserResult, validNewUser);
        assertEquals(validEdgeLoginResult, validEdgeNewUser);
    }

    @Test
    void register_userLoginAlreadyExists_NotOk() {
        User sameLoginUser = new User("Bob_Super", "Bob2345", 20);

        RegisterFailedException exception = assertThrows(RegisterFailedException.class,
                () -> service.register(sameLoginUser));
        assertEquals("User with login " + sameLoginUser.getLogin()
                + " already exists", exception.getMessage());
    }

    @Test
    void register_userIsNull_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.register(null));
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void register_loginLengthLessThanMinimalRequired_NotOk() {
        User wrongLoginUser = new User("Jack", "jack255", 55);
        User edgeWrongLoginUser = new User("JackJ", "jack255", 55);
        User nullLoginUser = new User(null, "jack255", 55);

        RegisterFailedException exception = assertThrows(RegisterFailedException.class, () ->
                service.register(wrongLoginUser));
        RegisterFailedException exceptionEdge = assertThrows(RegisterFailedException.class, () ->
                service.register(edgeWrongLoginUser));
        RegisterFailedException exceptionNull = assertThrows(RegisterFailedException.class, () ->
                service.register(nullLoginUser));

        String expectedMessage = "Login must be at least " + MIN_LOGIN_LENGTH
                + " characters.";

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedMessage, exceptionEdge.getMessage());
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_passwordLengthLessThanMinimalRequired_NotOk() {
        User wrongPasswordUser = new User("Jack123", "jack", 40);
        User edgePasswordUser = new User("Jack123", "jack1", 40);
        User nullPasswordUser = new User("Jack123", null, 40);

        RegisterFailedException exception = assertThrows(RegisterFailedException.class, () ->
                service.register(wrongPasswordUser));
        RegisterFailedException exceptionEdge = assertThrows(RegisterFailedException.class, () ->
                service.register(edgePasswordUser));
        RegisterFailedException exceptionNull = assertThrows(RegisterFailedException.class, () ->
                service.register(nullPasswordUser));

        String expectedMessage = "Password must be at least " + MIN_PASSWORD_LENGTH
                + " characters.";

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedMessage, exceptionEdge.getMessage());
        assertEquals(expectedMessage, exceptionNull.getMessage());
    }

    @Test
    void register_ageValueLessThanMinimalRequired_NotOk() {
        User wrongAgeUser = new User("AlexGL", "alex_gl", 10);
        User edgeAgeUser = new User("AlexGL", "alex_gl", 17);
        User nullAgeUser = new User("AlexGL", "alex_gl", null);

        RegisterFailedException exception = assertThrows(RegisterFailedException.class, () ->
                service.register(wrongAgeUser));
        RegisterFailedException exceptionEdge = assertThrows(RegisterFailedException.class, () ->
                service.register(edgeAgeUser));
        RegisterFailedException exceptionNull = assertThrows(RegisterFailedException.class, () ->
                service.register(edgeAgeUser));

        String expectedMessage = "User must be at least " + MIN_AGE + " years old.";

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedMessage, exceptionEdge.getMessage());
        assertEquals(expectedMessage, exceptionNull.getMessage());
    }
}
