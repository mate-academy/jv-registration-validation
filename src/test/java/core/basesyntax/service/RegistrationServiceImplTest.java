package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN_ONE_CHARACTER = "o";
    private static final String LOGIN_TWO_CHARACTER = "oo";
    private static final String LOGIN_THREE_CHARACTER = "oop";
    private static final String LOGIN_SIX_CHARACTERS = "validl";
    private static final String LOGIN_TEN_CHARACTERS = "validlogin";
    private static final String LOGIN_THIRTEEN_CHARACTERS = "validlogin123";
    private static final String PASSWORD_ONE_CHARACTER = "q";
    private static final String PASSWORD_THREE_CHARACTERS = "qwe";
    private static final String PASSWORD_FIVE_CHARACTERS = "qwert";
    private static final String PASSWORD_SIX_CHARACTERS = "qwerty";
    private static final String PASSWORD_NINE_CHARACTERS = "qwerty123";
    private static final String PASSWORD_TWELVE_CHARACTERS = "qwerty";
    private static final int AGE_EIGHTEEN = 18;
    private static final int AGE_TWENTY_FIVE = 25;
    private static final int AGE_THIRTY = 30;
    private static final int AGE_SEVENTEEN = 17;
    private static final int AGE_TWELVE = 12;
    private static final int AGE_SEVEN = 7;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_addUserWithCorrectData_ok() {
        User firstUser = new User();
        firstUser.setLogin(LOGIN_SIX_CHARACTERS);
        firstUser.setPassword(PASSWORD_SIX_CHARACTERS);
        firstUser.setAge(AGE_EIGHTEEN);
        User addedUser = registrationService.register(firstUser);
        assertEquals(firstUser, addedUser);
        assertTrue(Storage.people.contains(addedUser));
        User secondUser = new User();
        secondUser.setLogin(LOGIN_TEN_CHARACTERS);
        secondUser.setPassword(PASSWORD_NINE_CHARACTERS);
        secondUser.setAge(AGE_TWENTY_FIVE);
        addedUser = registrationService.register(secondUser);
        assertEquals(secondUser, addedUser);
        assertTrue(Storage.people.contains(addedUser));
        User thirdUser = new User();
        thirdUser.setLogin(LOGIN_THIRTEEN_CHARACTERS);
        thirdUser.setPassword(PASSWORD_TWELVE_CHARACTERS);
        thirdUser.setAge(AGE_THIRTY);
        addedUser = registrationService.register(thirdUser);
        assertEquals(thirdUser, addedUser);
        assertTrue(Storage.people.contains(addedUser));
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        User user = new User();
        user.setLogin(LOGIN_SIX_CHARACTERS);
        User secondUser = new User();
        secondUser.setLogin(LOGIN_SIX_CHARACTERS);
        Storage.people.add(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(secondUser));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin(LOGIN_ONE_CHARACTER);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(LOGIN_TWO_CHARACTER);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(LOGIN_THREE_CHARACTER);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin(LOGIN_SIX_CHARACTERS);
        user.setPassword(PASSWORD_ONE_CHARACTER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
        user.setPassword(PASSWORD_THREE_CHARACTERS);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
        user.setPassword(PASSWORD_FIVE_CHARACTERS);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin(LOGIN_SIX_CHARACTERS);
        user.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setLogin(LOGIN_SIX_CHARACTERS);
        user.setPassword(PASSWORD_SIX_CHARACTERS);
        user.setAge(AGE_SEVEN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(AGE_TWELVE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(AGE_SEVENTEEN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin(LOGIN_SIX_CHARACTERS);
        user.setPassword(PASSWORD_SIX_CHARACTERS);
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
