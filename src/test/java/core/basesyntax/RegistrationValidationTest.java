package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationValidationTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeAll
    public static void setUp() {
        User validUser = new User();
        validUser.setAge(18);
        validUser.setLogin("JohnTravolta");
        validUser.setPassword("P@ssw0rd");
        Storage.people.add(validUser);
    }

    @Test
    public void register_NullOnFieldsOfUser_NotOk() {
        User emptyUser = new User();
        Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(emptyUser));

    }

    @Test
    public void register_NormalUser_Ok() {
        User normalUser = new User();
        normalUser.setAge(28);
        normalUser.setLogin("JohnBush");
        normalUser.setPassword("P@ssw0rd");
        registrationService.register(normalUser);
        Assertions.assertEquals(normalUser, Storage.people.get(1));
    }

    @Test
    public void register_DuplicateLoginUser_NotOk() {
        User duplicateLoginUser = new User();
        duplicateLoginUser.setAge(21);
        duplicateLoginUser.setLogin("JohnTravolta");
        duplicateLoginUser.setPassword("Password23");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(duplicateLoginUser));
        Assertions.assertEquals("User with this login already registered!", exception.getMessage());
    }

    @Test
    public void register_UserLoginTooShort_NotOk() {
        User shortLoginUser = new User();
        shortLoginUser.setAge(21);
        shortLoginUser.setLogin("JBush");
        shortLoginUser.setPassword("Password23");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortLoginUser));
        Assertions.assertEquals("User login too short!", exception.getMessage());
    }

    @Test
    public void register_UserPasswordTooShort_NotOk() {
        User shortPasswordUser = new User();
        shortPasswordUser.setAge(21);
        shortPasswordUser.setLogin("AndriiKor");
        shortPasswordUser.setPassword("Pas13");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortPasswordUser));
        Assertions.assertEquals("User password too short!", exception.getMessage());
    }

    @Test
    public void register_UserAgeTooYoung_NotOk() {
        User youngUser = new User();
        youngUser.setAge(17);
        youngUser.setLogin("JohnBush");
        youngUser.setPassword("PaJohnBush13");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(youngUser));
        Assertions.assertEquals("User too young!", exception.getMessage());
    }

    @Test
    public void register_UserAgeTooOld_NotOk() {
        User oldUser = new User();
        oldUser.setAge(101);
        oldUser.setLogin("AlisaKim");
        oldUser.setPassword("PaJohnBush13");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(oldUser));
        Assertions.assertEquals("User too old!", exception.getMessage());
    }
}
