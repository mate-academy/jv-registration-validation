package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.exception.RegistrationIllegalArgumentException;
import core.basesyntax.model.User;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegistrationTests {
    private static final long ID1 = 1L;
    private static final long ID2 = 2L;
    private static final int NON_ACCEPTABLE_AGE = 9;
    private static final int ACCEPTABLE_AGE = 34;
    private static final String EMPTY_LOGIN = "";
    private static final String CORRECT_LOGIN = "Ukraine";
    private static final String INCORRECT_PASSWORD = "34df";
    private static final String CORRECT_PASSWORD = "GloryOfUkraine";
    private static RegistrationServiceImpl registrationService;
    static User validUser = new User();
    static User invalidUser = new User();


    @BeforeAll
    static void setUp() {
        System.out.println("----------------------  Tests begin  ----------------------");
        StorageDaoImpl storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        validUser.setId(ID1);
        validUser.setAge(ACCEPTABLE_AGE);
        validUser.setLogin(CORRECT_LOGIN);
        validUser.setPassword(CORRECT_PASSWORD);
        invalidUser.setId(ID2);
    }

    @BeforeEach
    void initialUsers() {
        invalidUser.setAge(NON_ACCEPTABLE_AGE);
        invalidUser.setLogin(EMPTY_LOGIN);
        invalidUser.setPassword(INCORRECT_PASSWORD);
    }

    @Test
    @DisplayName("Registration a NULL User object")
    public void register_null_user_NotOk() {
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(null);}, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for the null user login, but it wasn't");
        System.out.println("Test register_null_user_NotOk passed");
    }
    @Test
    @DisplayName("Registration an user with null login")
    public void register_user_with_null_login_NotOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(invalidUser);}, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with null login, but it wasn't");
        System.out.println("Test register_user_with_null_login_NotOk passed");
    }
    @Test
    @DisplayName("Registration an user with empty login")
    public void register_user_with_empty_login_NotOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(invalidUser);}, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for the null user login, but it wasn't");
        System.out.println("Test register_user_with_empty_login_NotOk passed");
    }
    @Test
    @DisplayName("Registration an user with null age")
    public void register_user_with_null_age_NotOk() {
        invalidUser.setAge(null);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(invalidUser);}, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with null age, but it wasn't");
        System.out.println("Test register_user_with_null_age_NotOk passed");
    }
    @Test
    @DisplayName("Registration an user with nonacceptable age")
    public void register_user_with_nonacceptable_age_NotOk() {
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(invalidUser);}, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with nonacceptable age, but it wasn't");
        System.out.println("Test register_user_with_nonacceptable_age_NotOk passed");
    }
    @Test
    @DisplayName("Registration an user with null password")
    public void register_user_with_null_password_NotOk() {
        invalidUser.setPassword(null);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(invalidUser);}, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with null password, but it wasn't");
        System.out.println("Test register_user_with_null_password_NotOk passed");
    }
    @Test
    @DisplayName("Registration an user with nonacceptable length of password")
    public void register_user_with_nonacceptable_password_NotOk() {
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(invalidUser);}, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for user with nonacceptable length of password, but it wasn't");
        System.out.println("Test register_user_with_nonacceptable_password_NotOk passed");
    }
    @Test
    @DisplayName("Repeatedly registration an user that was registered before")
    public void register_existing_user_again_NotOk() {
        registrationService.register(validUser);
        assertThrows(RegistrationIllegalArgumentException.class, () -> {
            registrationService.register(validUser);}, "Expected "
                + RegistrationIllegalArgumentException.class.getName()
                + " to be thrown for existing into Storage user, but it wasn't");
        System.out.println("Test register_existing_user_again_NotOk passed");
    }
    @AfterAll
    static void DoneTests() {
        System.out.println("----------------------  Tests are finished  ----------------------");
    }
}
