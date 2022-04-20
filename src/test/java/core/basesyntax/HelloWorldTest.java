package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class HelloWorldTest {
    private static RegistrationServiceImpl registration;
    private static StorageDao storage;

    @BeforeAll
    static void beforeAll() {
        registration = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @BeforeEach
    public void beforeEach() {
        Storage.people.clear();
    }

    @Test
    public void addingUser_Ok() {
        User user1 = new User("Login", "123456", 18);
        User user2 = new User("Login2", "123456", 18);
        User actualFirst = registration.register(user1);
        User actualSecond = registration.register(user2);
        Assert.assertEquals(user1, actualFirst);
        Assert.assertEquals(user2, actualSecond);
    }

    @Test
    public void wrongPassword_NotOK() {
        User user = new User("Login", "12345", 18);
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(user));
    }

    @Test
    public void wrongAge_NotOK() {
        User user = new User("Login", "123457", 17);
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(user));
    }

    @Test
    public void loginIsNull_NotOk() {
        User user = new User(null, "123457", 17);
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(user));
    }

    @Test
    public void loginAlreadyExist_NotOk() {
        User user1 = new User("Login", "123457", 18);
        User user2 = new User("Login", "7354787", 19);
        registration.register(user1);
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(user2));
    }

    @Test
    public void addingNullUser_NotOk() {
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(null));
    }
}
