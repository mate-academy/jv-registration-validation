package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService service;

    @BeforeAll
    public static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_ageLessThan18_notOk() {
        User user = new User();
        user.setAge(17);
        user.setLogin("user");
        user.setPassword("123456");
        Assertions.assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    public void register_passwordLengthLessThan6_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("user");
        user.setPassword("12345");
        Assertions.assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    public void register_loginAlreadyExists_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("user");
        user.setPassword("123456");
        service.register(user);
        Assertions.assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    public void register_userIsNull_notOk() {
        Assertions.assertThrows(ValidationException.class, () -> service.register(null));
    }

    @Test
    public void register_loginIsNull_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin(null);
        user.setPassword("123456");
        Assertions.assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    public void register_passwordIsNull_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("user");
        user.setPassword(null);
        Assertions.assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    public void register_ok() {
        User user = new User();
        user.setAge(19);
        user.setLogin("user");
        user.setPassword("123456");
        StorageDao dao = new StorageDaoImpl();
        User registeredUser = dao.add(user);
        Assertions.assertEquals(1, (long) registeredUser.getId());
        Assertions.assertEquals(Storage.people.get(0), registeredUser);
    }
}
