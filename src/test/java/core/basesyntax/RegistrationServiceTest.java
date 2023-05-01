package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegisterException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String MIN_CHARACTER = "A";
    private static final String SHORT_STRING = "TEST";
    private static final int MIN_USER_AGE = 1;
    private static final int NEGATIVE_USER_AGE = -15;
    private static RegistrationService registrationService;
    private static List<User> usersIsValid;
    private static User user;
    
    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        usersIsValid = new ArrayList<>();
        
    }

    @BeforeEach
    public void setUp() {
        user = new User("userlogin", "password", 21);
    }

    @Test
    public void userRegister_Login_Ok() {
        initUsersVaild();
        for (User user : usersIsValid) {
            registrationService.register(user);
        }
        assertEquals(Storage.people, usersIsValid,"User list is not equal to database");
    }

    @Test
    public void userRegister_LoginMinChar_NotOK() {
        user.setLogin(MIN_CHARACTER);
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_LoginShortStr_NotOK() {
        user.setLogin(SHORT_STRING);
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_LoginIsNull_NotOK() {
        user.setLogin(null);
        
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_LoginIsEmpty_NotOK() {
        user.setLogin("");
        
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_PassMinChar_NotOK() {
        user.setPassword(MIN_CHARACTER);
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_PassShortStr_NotOK() {
        user.setPassword(SHORT_STRING);
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_PassIsNull_NotOK() {
        user.setPassword(null);
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_PassIsEmpty_NotOK() {
        user.setPassword("");
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_minAge_NotOK() {
        user.setAge(MIN_USER_AGE);
        
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_negativeAge_NotOK() {
        user.setAge(NEGATIVE_USER_AGE);
        
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void userRegister_isNullAge_NotOK() {
        user.setAge(null);
        
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }
    
    @Test
    public void userRegister_isNumNullAge_NotOK() {
        user.setAge(0);
        
        assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    private void initUsersVaild() {
        usersIsValid.add(new User("admink", "password", 18));
        usersIsValid.add(new User("abcdefte", "abmeadad", 40));
        usersIsValid.add(new User("Jordan", "vvjjooeeqasdgrw", 100));
    }
}
