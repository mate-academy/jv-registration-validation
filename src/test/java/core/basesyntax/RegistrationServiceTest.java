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

class RegistrationServiceTest {
    private static final int ADULT_AGE = 18;
    private static final int ANOTHER_ADULT_AGE = 25;
    private static final int NEGATIVE_AGE = -10;
    private static final int TEEN_AGE = 17;
    private static final int ZERO_AGE = 0;

    private static RegistrationService registrationService;
    private User john;
    private User jane;
    private User maria;
    private User george;
    private String validPassword;
    private String anotherValidPassword;
    private String invalidPassword;
    private String shortPassword;
    private String validLogin;
    private String anotherValidLogin;
    private String thirdValidLogin;
    private String invalidLogin;
    private String shortLogin;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        john = new User();
        jane = new User();
        maria = new User();
        george = new User();
        validPassword = "qwerty";
        anotherValidPassword = "qwe123rty";
        invalidPassword = "abc12";
        shortPassword = "ox";
        validLogin = "UserName";
        anotherValidLogin = "AcceptableLogin";
        thirdValidLogin = "MyName";
        invalidLogin = "NameX";
        shortLogin = "Rw";
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_DifferentValidUsers_Ok() {
        john.setAge(ADULT_AGE);
        john.setLogin(validLogin);
        john.setPassword(validPassword);
        maria.setAge(ANOTHER_ADULT_AGE);
        maria.setLogin(anotherValidLogin);
        maria.setPassword(anotherValidPassword);
        User actual = registrationService.register(john);
        assertEquals(actual, john);
        User nextUserToAdd = registrationService.register(maria);
        assertEquals(nextUserToAdd, maria);
    }

    @Test
    void register_underageUser_NotOk() {
        john.setAge(TEEN_AGE);
        john.setLogin(validLogin);
        john.setPassword(validPassword);
        assertThrows(InvalidDataException.class, () -> registrationService.register(john));
    }

    @Test
    void register_inadmissibleAge_NotOK() {
        george.setAge(ZERO_AGE);
        george.setLogin(anotherValidLogin);
        george.setPassword(anotherValidPassword);
        jane.setAge(NEGATIVE_AGE);
        jane.setLogin(thirdValidLogin);
        jane.setPassword(validPassword);
        assertThrows(InvalidDataException.class, () -> registrationService.register(george));
        assertThrows(InvalidDataException.class, () -> registrationService.register(jane));
    }

    @Test
    void register_shortLogin_NotOk() {
        john.setAge(ADULT_AGE);
        john.setLogin(invalidLogin);
        john.setPassword(validPassword);
        george.setAge(ANOTHER_ADULT_AGE);
        george.setLogin(shortLogin);
        george.setPassword(anotherValidPassword);
        assertThrows(InvalidDataException.class, () -> registrationService.register(john));
        assertThrows(InvalidDataException.class, () -> registrationService.register(george));
    }

    @Test
    void register_shortPassword_NotOk() {
        john.setAge(ANOTHER_ADULT_AGE);
        john.setLogin(validLogin);
        john.setPassword(invalidPassword);
        george.setAge(ADULT_AGE);
        george.setLogin(thirdValidLogin);
        george.setPassword(shortPassword);
        assertThrows(InvalidDataException.class, () -> registrationService.register(john));
        assertThrows(InvalidDataException.class, () -> registrationService.register(george));
    }

    @Test
    void register_emptyLogin_NotOk() {
        john.setAge(ADULT_AGE);
        john.setLogin("");
        john.setPassword(validPassword);
        assertThrows(InvalidDataException.class, () -> registrationService.register(john));
    }

    @Test
    void register_emptyPassword_NotOK() {
        john.setAge(ADULT_AGE);
        john.setLogin(validLogin);
        john.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(john));
    }

    @Test
    void register_nullLogin_NotOk() {
        john.setAge(ANOTHER_ADULT_AGE);
        john.setLogin(null);
        john.setPassword(validPassword);
        assertThrows(InvalidDataException.class, () -> registrationService.register(john));
    }

    @Test
    void register_nullPassword_NotOk() {
        john.setAge(ADULT_AGE);
        john.setLogin(validLogin);
        john.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(john));
    }

    @Test
    void register_duplicateUser_NotOk() {
        john.setAge(ANOTHER_ADULT_AGE);
        john.setLogin(validLogin);
        john.setPassword(validPassword);
        Storage.people.add(john);
        assertThrows(InvalidDataException.class, () -> registrationService.register(john));
    }

    @Test
    void register_userWithExistingLogin_NotOk() {
        john.setAge(ANOTHER_ADULT_AGE);
        john.setLogin(validLogin);
        john.setPassword(validPassword);
        Storage.people.add(john);
        maria.setAge(ANOTHER_ADULT_AGE);
        maria.setLogin(validLogin);
        maria.setPassword(anotherValidPassword);
        assertThrows(InvalidDataException.class, () -> registrationService.register(maria));
    }
}
