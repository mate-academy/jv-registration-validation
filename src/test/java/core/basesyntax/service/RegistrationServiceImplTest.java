package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static User user;
    private static final String loginOrPassEmpty = "";
    private static final String loginOrPassOneChar = "s";
    private static final String loginOrPassThreeChars = "sho";
    private static final String loginOrPassFive5Chars = "short";
    private static final String loginOrPassSixChars = "valide";
    private static final String loginOrPassSevenChars = "valided";
    private static final String loginOrPassEightChars = "corrects";
    private static final int ageNegative = -2;
    private static final int ageFourteen = 14;
    private static final int ageSeventeen = 17;
    private static final int ageEightteen = 18;
    private static final int ageNineteen = 19;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(loginOrPassSixChars, loginOrPassSixChars, ageEightteen);
    }

    @Test
    void register_correctUser_ok() {
        User expected = new User(loginOrPassSixChars, loginOrPassSixChars, ageEightteen);
        User actual = service.register(user);
        assertEquals(expected, actual);

        expected = new User(loginOrPassSevenChars, loginOrPassSevenChars, ageNineteen);
        User newUser = expected;
        actual = service.register(newUser);
        assertEquals(expected, actual);

        expected = new User(loginOrPassEightChars, loginOrPassEightChars, ageNineteen);
        newUser = new User(loginOrPassEightChars, loginOrPassEightChars, ageNineteen);
        actual = service.register(newUser);
        assertEquals(expected, actual);
    }

    @Test
    void register_sameUser_notOk() {
        service.register(user);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullPass_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin(loginOrPassEmpty);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_emptyPass_notOk() {
        user.setPassword(loginOrPassEmpty);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_tooSmallLogin_notOk() {
        user.setLogin(loginOrPassOneChar);
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setLogin(loginOrPassThreeChars);
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setLogin(loginOrPassFive5Chars);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_tooSmallPass_notOk() {
        user.setPassword(loginOrPassOneChar);
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setPassword(loginOrPassThreeChars);
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setPassword(loginOrPassFive5Chars);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_tooSmallAge_notOk() {
        user.setAge(ageSeventeen);
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setAge(ageFourteen);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(ageNegative);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
