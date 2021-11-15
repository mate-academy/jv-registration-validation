package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private StorageDao storageDao;
    private User user;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp(){
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setAge(23);
        user1.setLogin("Alice");
        user1.setPassword("slfjdksls");
        user2.setAge(19);
        user2.setLogin("Bob");
        user2.setPassword("ksjadfaie");
        user3.setAge(35);
        user3.setLogin("Mark");
        user3.setPassword("uijkdjfkd");
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
    }

    @Test
    void register_NullAge_NotOk() {
        user = new User();
        user.setLogin("kate");
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));

    }
}