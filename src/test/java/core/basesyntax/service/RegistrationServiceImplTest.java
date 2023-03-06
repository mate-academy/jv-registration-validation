package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int MIN_AGE = 18;
    private static final String CORRECT_LOGIN = "newUser";
    private static final String CORRECT_PASSWORD = "123456";
    private static final String TOO_SHORT_PASSWORD = "12345";
    private static RegistrationService regService;
    private User correctUser;

    @BeforeAll
    static void init() {
        regService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        correctUser = createCorrectUser();
    }

    @AfterEach
    void cleanUp() {
        Storage.people.clear();
    }

    private User createCorrectUser() {
        User user = new User();
        user.setAge(MIN_AGE);
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        return user;
    }

    @Test
    void register_UserNull_NotOk() {
        assertThrows(RegistrationException.class, () -> regService.register(null),
                "Registered null user");
    }

    @Test
    void register_AgeUnderMin_NotOk() {
        correctUser.setAge(MIN_AGE - 1);
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user with age less than minimum without throwing an exception");
    }

    @Test
    void register_NegativeAge_NotOk() {
        correctUser.setAge(-1);
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user with negative age value without throwing an exception");
    }

    @Test
    void register_ZeroAge_NotOk() {
        correctUser.setAge(0);
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user with zero age without throwing an exception");
    }

    @Test
    void register_NullAge_NotOk() {
        correctUser.setAge(null);
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user with age null without throwing an exception");
    }

    @Test
    void register_AgeMin_Ok() {
        correctUser.setAge(MIN_AGE);
        assertEquals(correctUser, regService.register(correctUser),
                "User with minimum age wasn't registered");
        assertTrue(Storage.people.contains(correctUser),
                "User with minimum age wasn't added to database");
    }

    @Test
    void register_AgeOverMin_Ok() {
        correctUser.setAge(MIN_AGE + 1);
        assertEquals(correctUser, regService.register(correctUser),
                "User with age above minimum wasn't registered");
        assertTrue(Storage.people.contains(correctUser),
                "User with age above minimum wasn't added to database");
    }

    @Test
    void register_NullLogin_NotOk() {
        correctUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user with Login null without throwing an exception");
    }

    @Test
    void register_ZeroLengthLogin_NotOk() {
        correctUser.setLogin("");
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user with empty Login without throwing an exception");
    }

    @Test
    void register_NullPassword_NotOk() {
        correctUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user with null password without throwing an exception");
    }

    @Test
    void register_ShortPassword_NotOk() {
        correctUser.setPassword(TOO_SHORT_PASSWORD);
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user with password shorter than minimum length "
                        + "without throwing an exception");
    }

    @Test
    void register_UserAlreadyAdded_NotOk() {
        Storage.people.add(correctUser);
        assertThrows(RegistrationException.class, () -> regService.register(correctUser),
                "Registered a user that is already added "
                        + "to database without throwing an exception");
    }

    @Test
    void register_SavedInStorageWithNullID_NotOk() {
        regService.register(correctUser);
        int indexDB = Storage.people.indexOf(correctUser);
        assertNotNull(Storage.people.get(indexDB).getId());
    }

    @Test
    void register_MultipleCorrectUsers_Ok() {
        int testUsersAmount = 100;
        for (int i = 0; i < testUsersAmount; i++) {
            correctUser = createCorrectUser();
            correctUser.setLogin("newUser" + i);
            assertEquals(correctUser, regService.register(correctUser));
            assertTrue(Storage.people.contains(correctUser));
        }
        assertEquals(testUsersAmount, Storage.people.size());
    }
}
