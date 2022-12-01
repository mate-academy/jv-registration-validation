package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User defaultUser;
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        defaultUser = new User();
        defaultUser.setAge(22);
        defaultUser.setPassword("123password123");
        defaultUser.setLogin("user1");
        defaultUser.setId(451232L);
    }

    @Test
    public void register_Ok() {
        registrationService.register(defaultUser);
    }

    @Test
    public void register_ageLess_notOk() {
        User user = new User();
        user.setPassword("password1234");
        user.setAge(17);
        user.setLogin("user2");
        user.setId(458962L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLengthLess_notOk() {
        User user = new User();
        user.setPassword("123");
        user.setAge(21);
        user.setLogin("user2");
        user.setId(458962L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setPassword(null);
        user.setAge(45);
        user.setLogin("user3");
        user.setId(45987654L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword("password");
        user.setAge(45);
        user.setLogin(null);
        user.setId(451234564L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setPassword("password");
        user.setAge(null);
        user.setLogin("user228");
        user.setId(451234564L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_StorageContainsLogin_notOk() {
        final StorageDao storage = new StorageDaoImpl();
        User user1 = new User();
        user1.setId(1234567L);
        user1.setLogin("user1");
        user1.setAge(25);
        user1.setPassword("password1111");
        storage.add(user1);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }
}
