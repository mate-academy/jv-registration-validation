package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeAll
    public static void setUp() {
        User validUser = new User();
        validUser.setAge(18);
        validUser.setLogin("Cherkassy");
        validUser.setPassword("Qwerty3");
        Storage.people.add(validUser);
    }

    @Test
    public void register_NullOnFieldsOfUser_NotOk() {
        User emptyUser = new User();
        Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(emptyUser));
    }

    @Test
    public void register_ValidUser_Ok() {
        User suitableUser = new User();
        suitableUser.setAge(52);
        suitableUser.setLogin("Uzhgorod");
        suitableUser.setPassword("Pass@WorD");
        registrationService.register(suitableUser);
        Assertions.assertEquals(suitableUser, Storage.people.get(1));
    }

    @Test
    public void register_DuplicateLogin_NotOk() {
        User duplicateLogin = new User();
        duplicateLogin.setAge(45);
        duplicateLogin.setLogin("Cherkassy");
        duplicateLogin.setPassword("MT69@11");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(duplicateLogin));
        Assertions.assertEquals("This login is already assigned!", exception.getMessage());
    }

    @Test
    public void register_AgeYoungUser_NotOk() {
        User youngUser = new User();
        youngUser.setAge(14);
        youngUser.setLogin("Kropyvnytskii");
        youngUser.setPassword("MYR3655");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(youngUser));
        Assertions.assertEquals("This user is so young!", exception.getMessage());
    }

    @Test
    public void register_AgeOldUser_NotOk() {
        User oldUser = new User();
        oldUser.setAge(103);
        oldUser.setLogin("Chernigiv");
        oldUser.setPassword("Mlyn51");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(oldUser));
        Assertions.assertEquals("This user is old!", exception.getMessage());
    }

    @Test
    public void register_ShortUserLogin_NotOk() {
        User shortUserLogin = new User();
        shortUserLogin.setAge(38);
        shortUserLogin.setLogin("Kyiv");
        shortUserLogin.setPassword("PO@1235");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortUserLogin));
        Assertions.assertEquals("This login is short!", exception.getMessage());
    }

    @Test
    public void register_ShortUserPassword_NotOk() {
        User shortUserPassword = new User();
        shortUserPassword.setAge(45);
        shortUserPassword.setLogin("Mykolaiv");
        shortUserPassword.setPassword("KI23");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortUserPassword));
        Assertions.assertEquals("This password is short!", exception.getMessage());
    }
}
