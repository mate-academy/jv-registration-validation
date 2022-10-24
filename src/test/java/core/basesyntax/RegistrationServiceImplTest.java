package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user1;

    @BeforeEach
    public void init() {
        registrationService = new RegistrationServiceImpl();
        user1 = new User("spider@gmail.com", "qwerty12345", 25);
    }

    @AfterEach
    public void clear() {
        user1 = null;
        Storage.people.clear();
    }

    @Test
    public void register_alreadyExistedUser_notOk() {
        Storage.people.add(user1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    public void register_nullUser_notOk() {
        user1 = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user1));
    }

    @Test
    public void register_adult_notOk() {
        user1.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    public void register_lengthOfPassword_notOk() {
        user1.setPassword("sdf");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_nullLoginInUser_notOk() {
        user1.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_nullPasswordInUser_notOk() {
        user1.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_nullAgeInUser_notOk() {
        user1.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user1));
        clear();
    }
}
