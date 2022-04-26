package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static User user;
    private static RegistrationService registrationService;

    @BeforeAll
   static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullAge_notOk() {
        user = new User();
        user.setAge(null);
        user.setLogin("null");
        user.setPassword("user-null");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_lessThanEighteenAge_notOk() {
        user = new User();
        user.setAge(16);
        user.setLogin("user");
        user.setPassword("user16");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_greaterOrEqualEighteenAge_ok() {
        User user = new User();
        user.setAge(22);
        user.setLogin("Bob");
        user.setPassword("boby22");
        registrationService.register(user);
        assertEquals(Storage.people.get(0).getAge(), user.getAge());
    }

    @Test
    public void register_negativeAge_notOk() {
        user = new User();
        user.setAge(-18);
        user.setLogin("user-18");
        user.setPassword("user-18");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordNull_notOk() {
        user = new User();
        user.setAge(22);
        user.setLogin("user22");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordCharactersLessThanSix_notOk() {
        user = new User();
        user.setAge(22);
        user.setLogin("user22");
        user.setPassword("user");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordCharactersMoreThanSix_ok() {
        user = new User();
        user.setAge(22);
        user.setLogin("user22");
        user.setPassword("user22");
        registrationService.register(user);
        assertEquals(Storage.people.get(0).getPassword().length(), user.getPassword().length());
    }

    @Test
    public void register_loginNull_notOk() {
        user = new User();
        user.setAge(22);
        user.setLogin(null);
        user.setPassword("user22");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
