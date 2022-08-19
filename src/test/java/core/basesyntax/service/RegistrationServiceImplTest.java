package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registeredUser;
    private static StorageDao getUser;
    private static final User userGood1 = new User();
    private static final User userGood2 = new User();
    private static final User userDouble1 = new User();
    private static final User userDouble2 = new User();
    private static final User userNull = null;
    private static final User userTooYoung = new User();
    private static final User userTooOld = new User();
    private static final User userNegativeAge = new User();
    private static final User userNullAge = new User();
    private static final User userWrongLogin = new User();
    private static final User userNullLogin = new User();
    private static final User userWrongPassword = new User();
    private static final User userNullPassword = new User();

    @BeforeAll
    public  static void setUp() {
        registeredUser = new RegistrationServiceImpl();
        getUser = new StorageDaoImpl();
        userGood1.setAge(22);
        userGood1.setPassword("crimeabridgedestroyed");
        userGood1.setLogin("Himars");
        userGood2.setAge(70);
        userGood2.setPassword("freedom");
        userGood2.setLogin("Bayraktar");
        userDouble1.setAge(22);
        userDouble1.setPassword("crimeabridgedestroyed");
        userDouble1.setLogin("Himars");
    }

    @Test
    public void registerAndGetNewUser_Ok() {
        User actualUser1 = registeredUser.register(userGood1);
        User expectedUser1 = getUser.get(userGood1.getLogin());
        assertEquals(actualUser1, expectedUser1);
        User actualUser2 = registeredUser.register(userGood2);
        User expectedUser2 = getUser.get(userGood2.getLogin());
        assertEquals(actualUser2, expectedUser2);
        assertNotEquals(actualUser1, actualUser2);
    }

    @Test
    void registerAndGetDifferentUsers_Ok() {
        User actualUser1 = registeredUser.register(userGood1);
        User expectedUser1 = getUser.get(actualUser1.getLogin());
        User actualUser2 = registeredUser.register(userGood2);
        User expectedUser2 = getUser.get(actualUser2.getLogin());
        assertNotEquals(expectedUser1, expectedUser2);
    }

    @Test
    void userNull_Ok() {
        assertThrows(NullPointerException.class, () -> registeredUser.register(userNull));
    }

    @Test
    void userDouble_Exception_Ok() {
        User actualUser1 = registeredUser.register(userGood1);
        assertThrows(NullPointerException.class, () -> registeredUser.register(userDouble1));
    }
}