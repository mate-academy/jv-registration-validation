package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptionforservice.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User actual;

    @BeforeEach
    public void setUser() {
        actual = new User();
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    public void checkAdedSameUsers() {
        actual.setLogin("boblogin45");
        actual.setPassword("password24");
        actual.setAge(25);
        User sameUser = new User(actual.getLogin(), actual.getPassword(), actual.getAge());
        registrationService.register(actual);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(sameUser), "This users are different");
    }

    @Test
    public void addingUsersWithSameData() {
        actual.setLogin("boblogin45");
        actual.setPassword("password24");
        actual.setAge(25);
        registrationService.register(actual);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "This user doesn't exists!");
    }

    @Test
    public void addingUsersWithDifferentData() {
        actual.setLogin("login245");
        actual.setPassword("password24");
        actual.setAge(23);
        registrationService.register(actual);
        User secondAdedUser = new User("boblog24", "bobpasword", 20);
        registrationService.register(secondAdedUser);
        int sizeStorage = Storage.people.size();
        assertEquals(2, sizeStorage);
    }

    @Test
    public void checkNullInPassword() {
        actual.setLogin("login234");
        actual.setPassword(null);
        actual.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Password was correct");
    }

    @Test
    public void checkNullInLogin() {
        actual.setLogin(null);
        actual.setPassword("password32");
        actual.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Login was correct");
    }

    @Test
    public void checkIncorrectPassword() {
        actual.setLogin("loginbob");
        actual.setPassword("3242");
        actual.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Password was correct");
    }

    @Test
    public void checkIncorrectLogin() {
        actual.setLogin("fken");
        actual.setPassword("password32");
        actual.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Login was correct");
    }

    @Test
    public void checkIncorrectAge() {
        actual.setLogin("boblogin");
        actual.setPassword("password32");
        actual.setAge(10);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Age was correct");
    }
}
