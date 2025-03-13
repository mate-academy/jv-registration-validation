package core.basesyntax;

import core.basesyntax.model.User;
import core.basesyntax.service.Errors;
import core.basesyntax.service.RegistrationError;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static RegistrationService registrationUser;
    private static User user;


    @BeforeAll
    static void beforeAll() {
        registrationUser = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_NullLogin_ThrowsError() {
        user.setLogin(null);
        user.setPassword("J2341fsa");
        user.setAge(20);
        RegistrationError registrationError = assertThrows(RegistrationError.class, () -> registrationUser.register(user));
        assertEquals(Errors.Login_NotNull.getMessage(), registrationError.getMessage());
    }

    @Test
    void register_NullPassword_ThrowsError() {
        user.setLogin("Notiysd");
        user.setPassword(null);
        user.setAge(29);
        RegistrationError registrationError = assertThrows(RegistrationError.class, () -> registrationUser.register(user));
        assertEquals(Errors.Password_NotNull.getMessage(),registrationError.getMessage());

    }

    @Test
    void register_NullAge_ThrowsError() {
        user.setLogin("Jokinols");
        user.setPassword("24123asfS");
        user.setAge(null);
        RegistrationError registrationError = assertThrows(RegistrationError.class, () -> registrationUser.register(user));
        assertEquals(Errors.Age_NotNull.getMessage(),registrationError.getMessage());
    }

    @Test
    void register_ShortLogin_ThrowsError() {
        user.setLogin("Helao");
        user.setPassword("12312325");
        user.setAge(35);
        RegistrationError registrationError = assertThrows(RegistrationError.class, () -> registrationUser.register(user));
        assertEquals(Errors.Short_UserLogin.getMessage(),registrationError.getMessage());
    }

    @Test
    void register_ShortPassword_ThrowsError() {
        user.setLogin("asxcbd");
        user.setPassword("3213");
        user.setAge(25);
        RegistrationError registrationError = assertThrows(RegistrationError.class, () -> registrationUser.register(user));
        assertEquals(Errors.Short_UserPassword.getMessage(),registrationError.getMessage());
    }

    @Test
    void register_YoungAge_ThrowsError() {
        user.setLogin("Sddgsfg");
        user.setPassword("wsdfg24xcv");
        user.setAge(17);
        RegistrationError registrationError = assertThrows(RegistrationError.class, () -> registrationUser.register(user));
        assertEquals(Errors.User_AgeYounger.getMessage(), registrationError.getMessage());

    }

    @Test
    void register_Ok() {
        user.setLogin("Hellsdao");
        user.setPassword("341234");
        user.setAge(24);
        assertDoesNotThrow(() -> registrationUser.register(user));
    }

    @Test
    void register_DuplicateLogin_ThrowsError() {
        user.setLogin("Hellsdao");
        user.setPassword("23425fs");
        user.setAge(42);
        RegistrationError registrationError = assertThrows(RegistrationError.class, () -> registrationUser.register(user));
        assertEquals(Errors.Login_inUse.getMessage(),registrationError.getMessage());
    }
}

