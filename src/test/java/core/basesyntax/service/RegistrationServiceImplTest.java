package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int AGE_OF_TWO = 2;
    private static final int AGE_OF_FIVE = 5;
    private static final int AGE_OF_TEN = 10;
    private static final int AGE_OF_EIGHTEEN = 18;
    private static final int AGE_OF_TWENTY = 20;
    private static final int AGE_OF_FORTY = 40;
    private static final int NEGATIVE_AGE_OF_MINUS_FIVE = -5;
    private static final int NEGATIVE_AGE_OF_MINUS_TWENTY = -20;
    private static final int NEGATIVE_AGE_OF_MINUS_FIFTY = -50;
    private static final String GOOD_LOGIN_FIRST = "login12345";
    private static final String GOOD_LOGIN_SECOND = "admin12345";
    private static final String GOOD_LOGIN_THIRD = "superAdmin12345";
    private static final String GOOD_LOGIN_FORTH = "superlogin12345";
    private static final String GOOD_LOGIN_FIFTH = "admin12345fff";
    private static final String GOOD_LOGIN_SIXTH = "superAdmin12345SDVDFA";
    private static final String GOOD_LOGIN_SEVENTH = "login12345sdsfvs";
    private static final String GOOD_LOGIN_EIGHT = "admin123457e7ch";
    private static final String GOOD_LOGIN_NINTH = "superAdmin12345asadvc";
    private static final String GOOD_LOGIN_TENTH = "superAdmin12345asadvcDSSDCADS";
    private static final String GOOD_PASSWORD_FIRST = "password12345";
    private static final String GOOD_PASSWORD_SECOND = "password3888112";
    private static final String GOOD_PASSWORD_THIRD = "passwordMkH61345555";
    private static final String EMPTY_LOGIN_OR_PASSWORD = "";
    private static final String LOGIN_LENGTH_OF_TWO = "1h";
    private static final String LOGIN_LENGTH_OF_FOUR = "ABCD";
    private static final String PASSWORD_LENGTH_OF_THREE = "j2k";
    private static final String PASSWORD_LENGTH_OF_FIVE = "KHw7R";
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void createReference() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userAlreadyExist_notOk() {
        User user = new User();
        user.setLogin(GOOD_LOGIN_FIRST);
        user.setPassword(GOOD_PASSWORD_FIRST);
        user.setAge(AGE_OF_TWENTY);
        Storage.people.add(user);
        for (int i = 0; i < 5; i++) {
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    void register_nullAge_notOk() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(GOOD_LOGIN_FIRST);
        user2.setLogin(GOOD_LOGIN_SECOND);
        user3.setLogin(GOOD_LOGIN_THIRD);
        user1.setPassword(GOOD_PASSWORD_FIRST);
        user2.setPassword(GOOD_PASSWORD_SECOND);
        user3.setPassword(GOOD_PASSWORD_THIRD);
        user1.setAge(null);
        user2.setAge(null);
        user3.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_nullLogin_notOk() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(null);
        user2.setLogin(null);
        user3.setLogin(null);
        user1.setPassword(GOOD_PASSWORD_FIRST);
        user2.setPassword(GOOD_PASSWORD_SECOND);
        user3.setPassword(GOOD_PASSWORD_THIRD);
        user1.setAge(AGE_OF_EIGHTEEN);
        user2.setAge(AGE_OF_TWENTY);
        user3.setAge(AGE_OF_FORTY);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_nullPassword_notOk() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(GOOD_LOGIN_FIRST);
        user2.setLogin(GOOD_LOGIN_SECOND);
        user3.setLogin(GOOD_LOGIN_THIRD);
        user1.setPassword(null);
        user2.setPassword(null);
        user3.setPassword(null);
        user1.setAge(AGE_OF_EIGHTEEN);
        user2.setAge(AGE_OF_TWENTY);
        user3.setAge(AGE_OF_FORTY);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_ageAboveMin_ok() throws RegistrationException {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(GOOD_LOGIN_SECOND);
        user2.setLogin(GOOD_LOGIN_THIRD);
        user3.setLogin(GOOD_LOGIN_FORTH);
        user1.setPassword(GOOD_PASSWORD_FIRST);
        user2.setPassword(GOOD_PASSWORD_SECOND);
        user3.setPassword(GOOD_PASSWORD_THIRD);
        user1.setAge(AGE_OF_EIGHTEEN);
        user2.setAge(AGE_OF_TWENTY);
        user3.setAge(AGE_OF_FORTY);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertEquals(AGE_OF_EIGHTEEN, user1.getAge());
        assertEquals(AGE_OF_TWENTY, user2.getAge());
        assertEquals(AGE_OF_FORTY, user3.getAge());
    }

    @Test
    void register_ageBelowMin_notOk() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(GOOD_LOGIN_FIRST);
        user2.setLogin(GOOD_LOGIN_SECOND);
        user3.setLogin(GOOD_LOGIN_THIRD);
        user1.setPassword(GOOD_PASSWORD_FIRST);
        user2.setPassword(GOOD_PASSWORD_SECOND);
        user3.setPassword(GOOD_PASSWORD_THIRD);
        user1.setAge(AGE_OF_TWO);
        user2.setAge(AGE_OF_FIVE);
        user3.setAge(AGE_OF_TEN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_negativeAge_notOk() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(GOOD_LOGIN_FIRST);
        user2.setLogin(GOOD_LOGIN_SECOND);
        user3.setLogin(GOOD_LOGIN_THIRD);
        user1.setPassword(GOOD_PASSWORD_FIRST);
        user2.setPassword(GOOD_PASSWORD_SECOND);
        user3.setPassword(GOOD_PASSWORD_THIRD);
        user1.setAge(NEGATIVE_AGE_OF_MINUS_FIVE);
        user2.setAge(NEGATIVE_AGE_OF_MINUS_TWENTY);
        user3.setAge(NEGATIVE_AGE_OF_MINUS_FIFTY);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_loginLengthAboveMin_ok() throws RegistrationException {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(GOOD_LOGIN_FIFTH);
        user2.setLogin(GOOD_LOGIN_SIXTH);
        user3.setLogin(GOOD_LOGIN_SEVENTH);
        user1.setPassword(GOOD_PASSWORD_FIRST);
        user2.setPassword(GOOD_PASSWORD_SECOND);
        user3.setPassword(GOOD_PASSWORD_THIRD);
        user1.setAge(AGE_OF_EIGHTEEN);
        user2.setAge(AGE_OF_TWENTY);
        user3.setAge(AGE_OF_FORTY);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertEquals(GOOD_LOGIN_FIFTH, user1.getLogin());
        assertEquals(GOOD_LOGIN_SIXTH, user2.getLogin());
        assertEquals(GOOD_LOGIN_SEVENTH, user3.getLogin());
    }

    @Test
    void register_loginLengthBelowMin_notOk() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(EMPTY_LOGIN_OR_PASSWORD);
        user2.setLogin(LOGIN_LENGTH_OF_TWO);
        user3.setLogin(LOGIN_LENGTH_OF_FOUR);
        user1.setPassword(GOOD_PASSWORD_FIRST);
        user2.setPassword(GOOD_PASSWORD_SECOND);
        user3.setPassword(GOOD_PASSWORD_THIRD);
        user1.setAge(AGE_OF_EIGHTEEN);
        user2.setAge(AGE_OF_TWENTY);
        user3.setAge(AGE_OF_FORTY);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_passwordLengthAboveMin_ok() throws RegistrationException {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(GOOD_LOGIN_EIGHT);
        user2.setLogin(GOOD_LOGIN_NINTH);
        user3.setLogin(GOOD_LOGIN_TENTH);
        user1.setPassword(GOOD_PASSWORD_FIRST);
        user2.setPassword(GOOD_PASSWORD_SECOND);
        user3.setPassword(GOOD_PASSWORD_THIRD);
        user1.setAge(AGE_OF_EIGHTEEN);
        user2.setAge(AGE_OF_TWENTY);
        user3.setAge(AGE_OF_FORTY);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertEquals(GOOD_PASSWORD_FIRST, user1.getPassword());
        assertEquals(GOOD_PASSWORD_SECOND, user2.getPassword());
        assertEquals(GOOD_PASSWORD_THIRD, user3.getPassword());
    }

    @Test
    void register_passwordLengthBelowMin_notOk() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin(GOOD_LOGIN_FIRST);
        user2.setLogin(GOOD_LOGIN_SECOND);
        user3.setLogin(GOOD_LOGIN_THIRD);
        user1.setPassword(EMPTY_LOGIN_OR_PASSWORD);
        user2.setPassword(PASSWORD_LENGTH_OF_THREE);
        user3.setPassword(PASSWORD_LENGTH_OF_FIVE);
        user1.setAge(AGE_OF_EIGHTEEN);
        user2.setAge(AGE_OF_TWENTY);
        user3.setAge(AGE_OF_FORTY);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }
}

