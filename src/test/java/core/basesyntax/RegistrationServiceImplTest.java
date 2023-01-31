package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String EXCEPTION_MESSAGE = RegistrationException.class.toString();

    private static final String VALID_LOGIN_FIRST = "firstDefaultLogin";
    private static final String VALID_LOGIN_SECOND = "secondDefaultLogin";
    private static final String VALID_LOGIN_THIRD = "thirdDefaultLogin";

    private static final String VALID_PASSWORD_FIRST = "123456";
    private static final String VALID_PASSWORD_SECOND = "LOOOOOOOOOOOOONG";
    private static final String VALID_PASSWORD_THIRD = "qwe123ZXC$%^";

    private static final int VALID_AGE_FIRST = 18;
    private static final int VALID_AGE_SECOND = 19;
    private static final int VALID_AGE_THIRD = 91;

    private static RegistrationServiceImpl registrationService;
    private List<User> validUsersList;

    private User validUserBob;
    private User validUserAlice;
    private User validUserJohn;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        validUserBob = new User();
        validUserBob.setLogin(VALID_LOGIN_FIRST);
        validUserBob.setPassword(VALID_PASSWORD_FIRST);
        validUserBob.setAge(VALID_AGE_FIRST);

        validUserAlice = new User();
        validUserAlice.setLogin(VALID_LOGIN_SECOND);
        validUserAlice.setPassword(VALID_PASSWORD_SECOND);
        validUserAlice.setAge(VALID_AGE_SECOND);

        validUserJohn = new User();
        validUserJohn.setLogin(VALID_LOGIN_THIRD);
        validUserJohn.setPassword(VALID_PASSWORD_THIRD);
        validUserJohn.setAge(VALID_AGE_THIRD);

        validUsersList = new ArrayList<>();
        validUsersList.add(validUserBob);
        validUsersList.add(validUserAlice);
        validUsersList.add(validUserJohn);
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_ValidUsers_Ok() {
        for (User user : validUsersList) {
            registrationService.register(user);
        }

        assertEquals(validUsersList.size(), Storage.people.size(),
                "Test failed! The size of the storage isn't correct. Expected "
                        + validUsersList.size() + " but was " + Storage.people.size() + '\n');
        assertTrue(Storage.people.contains(validUserBob),
                "Test failed! The storage must contain a user with login: "
                        + validUserBob.getLogin() + '\n');
        assertTrue(Storage.people.contains(validUserAlice),
                "Test failed! The storage must contain a user with login: "
                        + validUserAlice.getLogin() + '\n');
        assertTrue(Storage.people.contains(validUserJohn),
                "Test failed! The storage must contain a user with login: "
                        + validUserJohn.getLogin() + '\n');
    }

    @Test
    public void register_NullUser_NotAllowed() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                String.format("%S should be thrown for: User == null" + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    public void register_SameUser_NotAllowed() throws RegistrationException {
        registrationService.register(validUserBob);
        registrationService.register(validUserAlice);
        int expectedStorageSize = 2;
        assertEquals(expectedStorageSize, Storage.people.size(),
                "Test failed! The size isn't correct. Expected "
                        + expectedStorageSize + " but was " + Storage.people.size() + '\n');

        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: already existing login: "
                        + validUserBob.getLogin() + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    public void register_NullLogin_NotAllowed() {
        validUserBob.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: login == null" + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    public void register_EmptyLogin_NotAllowed() {
        validUserBob.setLogin("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: login is empty" + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    public void register_InvalidPasswordLength_NotAllowed() {
        validUserBob.setPassword("12345");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: not enough length for password: "
                        + validUserBob.getPassword() + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    public void register_NullPassword_NotAllowed() {
        validUserBob.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: password == null"
                        + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    public void register_NullAge_NotAllowed() {
        validUserBob.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: age == null" + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    public void register_InvalidAge_NotAllowed() {
        validUserBob.setAge(17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: user's age < 18!"
                        + '\n', EXCEPTION_MESSAGE));

        validUserBob.setAge(-19);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: user's age < 18!"
                        + '\n', EXCEPTION_MESSAGE));
    }
}
