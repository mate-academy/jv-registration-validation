package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationIllegalArgumentException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegistrationTests {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final int NON_ACCEPTABLE_AGE = 17;
    private static final int ACCEPTABLE_AGE = 18;
    private static final String EMPTY_LOGIN = "";
    private static final String CORRECT_LOGIN = "Ukraine";
    private static final String INCORRECT_PASSWORD = "34df";
    private static final String CORRECT_PASSWORD = "GloryOfUkraine";
    private static RegistrationServiceImpl registrationService;
    private static User testUser = new User();

    @BeforeAll
    public static void setUp() {
        System.out.println(ANSI_YELLOW
                + "----------------------  Tests started  ----------------------" + ANSI_RESET);
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    private void initialUser() {
        testUser.setAge(ACCEPTABLE_AGE);
        testUser.setLogin(CORRECT_LOGIN);
        testUser.setPassword(CORRECT_PASSWORD);
    }

    @AfterEach
    private void clearDoing() {
        Storage.people.clear();
    }

    @Test
    @DisplayName("Registration a NULL User object")
    public void register_null_user_NotOk() {
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(null); }, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for the null user, but it wasn't");
        System.out.println("Test register_null_user_NotOk passed");
    }

    @Test
    @DisplayName("Registration of user with null login")
    public void register_user_with_null_login_NotOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(testUser); }, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with null login, but it wasn't");
        System.out.println("Test register_user_with_null_login_NotOk passed");
    }

    @Test
    @DisplayName("Registration an user with empty login")
    public void register_user_with_empty_login_NotOk() {
        testUser.setLogin(EMPTY_LOGIN);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(testUser); }, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for the null user login, but it wasn't");
        System.out.println("Test register_user_with_empty_login_NotOk passed");
    }

    @Test
    @DisplayName("Registration an user with null age")
    public void register_user_with_null_age_NotOk() {
        testUser.setAge(null);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(testUser); }, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with null age, but it wasn't");
        System.out.println("Test register_user_with_null_age_NotOk passed");
    }

    @Test
    @DisplayName("Registration an user with nonacceptable age")
    public void register_user_with_nonacceptable_age_NotOk() {
        testUser.setAge(NON_ACCEPTABLE_AGE);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(testUser); }, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with nonacceptable age, but it wasn't");
        System.out.println("Test register_user_with_nonacceptable_age_NotOk passed");
    }

    @Test
    @DisplayName("Registration an user with null password")
    public void register_user_with_null_password_NotOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(testUser); }, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with null password, but it wasn't");
        System.out.println("Test register_user_with_null_password_NotOk passed");
    }

    @Test
    @DisplayName("Registration an user with nonacceptable length of password")
    public void register_user_with_nonacceptable_password_NotOk() {
        testUser.setPassword(INCORRECT_PASSWORD);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(testUser); }, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with nonacceptable length of password, but it wasn't");
        System.out.println("Test register_user_with_nonacceptable_password_NotOk passed");
    }

    @Test
    @DisplayName("Registration an new user with valid parameters")
    public void register_valid_user_Ok() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()),
                "Testing user present in Storage");
        System.out.println("Test register_valid_user_Ok passed");
    }

    @Test
    @DisplayName("Repeatedly registration an user that was registered before")
    public void register_existing_user_again_NotOk() {
        Storage.people.add(testUser);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(testUser); }, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for existing into Storage user, but it wasn't");
        System.out.println("Test register_existing_user_again_NotOk passed");
    }

    @AfterAll
    public static void doneTests() {
        System.out.println(ANSI_CYAN
                + "----------------------  Tests are finished  ----------------------"
                + ANSI_RESET);
    }
}
