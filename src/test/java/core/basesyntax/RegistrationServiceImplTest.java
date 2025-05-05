package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INDEX_OF_FIRST_USER = 0;
    private static final int INDEX_OF_SECOND_USER = 1;
    private static final long ID_FOR_FIRST_USER = 38452693L;
    private static final String LOGIN_FOR_FIRST_USER = "newboblogin";
    private static final String PASSWORD_FOR_FIRST_USER = "u48V8j207";
    private static final int AGE_FOR_FIRST_USER = 20;
    private static final long ID_FOR_SECOND_USER = 503684295L;
    private static final String LOGIN_FOR_SECOND_USER = "alicelogin";
    private static final String PASSWORD_FOR_SECOND_USER = "i5y8QJ4vc";
    private static final int AGE_FOR_SECOND_USER = 25;
    private static final String EMPTY_LOGIN = "";
    private static final String THREE_LETTER_LOGIN = "log";
    private static final String FIVE_LETTER_LOGIN = "short";
    private static final String SIX_LETTER_LOGIN = "abcdef";
    private static final String TEN_LETTER_LOGIN = "qwertyuiop";
    private static final String EMPTY_PASSWORD = "";
    private static final String THREE_LETTER_PASSWORD = "log";
    private static final String FIVE_LETTER_PASSWORD = "short";
    private static final String SIX_LETTER_PASSWORD = "abcdef";
    private static final String TEN_LETTER_PASSWORD = "qwertyuiop";
    private static final int FIRST_NEGATIVE_AGE = -23;
    private static final int SECOND_NEGATIVE_AGE = -1;
    private static final int ZERO_AGE = 0;
    private static final int FIRST_INSUFFICIENT_AGE = 5;
    private static final int SECOND_INSUFFICIENT_AGE = 17;
    private static final int FIRST_SUFFICIENT_AGE = 18;
    private static final int SECOND_SUFFICIENT_AGE = 26;
    private static RegistrationService registrationService;
    private List<User> users;

    @BeforeAll
    static void initializeRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void createUserList() {
        User user1 = new User();
        user1.setId(ID_FOR_FIRST_USER);
        user1.setLogin(LOGIN_FOR_FIRST_USER);
        user1.setPassword(PASSWORD_FOR_FIRST_USER);
        user1.setAge(AGE_FOR_FIRST_USER);

        User user2 = new User();
        user2.setId(ID_FOR_SECOND_USER);
        user2.setLogin(LOGIN_FOR_SECOND_USER);
        user2.setPassword(PASSWORD_FOR_SECOND_USER);
        user2.setAge(AGE_FOR_SECOND_USER);

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullId_notOk() {
        users.get(INDEX_OF_FIRST_USER).setId(null);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_nullLogin_notOk() {
        users.get(INDEX_OF_FIRST_USER).setLogin(null);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_nullPassword_notOk() {
        users.get(INDEX_OF_FIRST_USER).setPassword(null);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_nullAge_notOk() {
        users.get(INDEX_OF_FIRST_USER).setAge(null);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(users.get(INDEX_OF_SECOND_USER));

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        users.get(INDEX_OF_SECOND_USER).setLogin(EMPTY_LOGIN);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    void register_tooShortLogin_notOk() {
        users.get(INDEX_OF_SECOND_USER).setLogin(THREE_LETTER_LOGIN);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });

        users.get(INDEX_OF_SECOND_USER).setLogin(FIVE_LETTER_LOGIN);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    void register_sufficientLoginLength_ok() {
        users.get(INDEX_OF_SECOND_USER).setLogin(SIX_LETTER_LOGIN);

        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });

        users.get(INDEX_OF_FIRST_USER).setLogin(TEN_LETTER_LOGIN);

        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        users.get(INDEX_OF_SECOND_USER).setPassword(EMPTY_PASSWORD);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    void register_tooShortPassword_notOk() {
        users.get(INDEX_OF_SECOND_USER).setPassword(THREE_LETTER_PASSWORD);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });

        users.get(INDEX_OF_SECOND_USER).setPassword(FIVE_LETTER_PASSWORD);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    void register_sufficientPasswordLength_ok() {
        users.get(INDEX_OF_SECOND_USER).setPassword(SIX_LETTER_PASSWORD);

        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });

        users.get(INDEX_OF_FIRST_USER).setPassword(TEN_LETTER_PASSWORD);

        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_negativeAge_notOk() {
        users.get(INDEX_OF_FIRST_USER).setAge(FIRST_NEGATIVE_AGE);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });

        users.get(INDEX_OF_FIRST_USER).setAge(SECOND_NEGATIVE_AGE);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_zeroAge_notOk() {
        users.get(INDEX_OF_FIRST_USER).setAge(ZERO_AGE);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_insufficientAge_notOk() {
        users.get(INDEX_OF_FIRST_USER).setAge(FIRST_INSUFFICIENT_AGE);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });

        users.get(INDEX_OF_FIRST_USER).setAge(SECOND_INSUFFICIENT_AGE);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    void register_sufficientAge_ok() {
        users.get(INDEX_OF_FIRST_USER).setAge(FIRST_SUFFICIENT_AGE);

        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });

        users.get(INDEX_OF_SECOND_USER).setAge(SECOND_SUFFICIENT_AGE);

        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }
}
