package core.basesyntax;

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
    private static StorageDaoImpl storage;

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
    public void addingNormalUser() {
        User user1 = new User("Login", "123456", 18);
        User user2 = new User("Login2", "123456", 18);
        registration.register(user1);
        registration.register(user2);

        Assert.assertEquals(user2, storage.get(user2.getLogin()));
    }

    @Test
    public void checkIfPassword_NotOK() {
        User user = new User("Login", "12345", 18);
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(user));
    }

    @Test
    public void checkIfAge_NotOK() {
        User user = new User("Login", "123457", 17);
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(user));
    }

    @Test
    public void checkWhenLoginIsNull() {
        User user = new User(null, "123457", 17);
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(user));
    }

    @Test
    public void checkIfLoginAlreadyExist() {
        User user1 = new User("Login", "123457", 18);
        User user2 = new User("Login", "7354787", 19);
        registration.register(user1);
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(user2));
    }

    @Test
    public void addingNullUser() {
        Assert.assertThrows(RuntimeException.class, ()
                -> registration.register(null));
    }
}
