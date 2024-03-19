package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptionforservice.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User correct_user;
    private static User correct_user2;
    private static User null_password;
    private static User null_login;
    private static User invalid_password;
    private static User invalid_login;
    private static User invalid_age;
    private static User same_correct_user;

    @BeforeAll
    public static void setUp() {
        correct_user = new User("boblogin23", "bobpassword", 18);
        correct_user2 = new User("alicelogin", "alicepassword", 20);
        null_password = new User("boblogin", null, 25);
        null_login = new User(null, "password355",40);
        invalid_password = new User("login24","2324", 45);
        invalid_login = new User("ksf", "validpassword", 30);
        invalid_age = new User("boblogin", "bobpassword", 13);
        same_correct_user = new User(correct_user.getLogin(),
                correct_user.getPassword(), correct_user.getAge());
    }

    @BeforeEach
    public void setUser() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    public void checkSameUsers() {
        registrationService.register(correct_user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(same_correct_user),
                "This user doesn't exists!");
    }

    @Test
    public void checkAddDifferentUsers() {
        registrationService.register(correct_user);
        registrationService.register(correct_user2);
        int sizeStorage = Storage.people.size();
        assertEquals(2,sizeStorage);
    }

    @Test
    public void checkNullInPassword() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null_password),
                "Password was correct");
    }

    @Test
    public void checkNullInLogin() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null_login),
                "Login was correct");
    }

    @Test
    public void checkInvalidPassword() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalid_password),
                "Password was correct");
    }

    @Test
    public void checkInvalidLogin() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalid_login),
                "Login was correct");
    }

    @Test
    public void checkInvalidAge() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalid_age),
                "Age was correct");
    }
}
