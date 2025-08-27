package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user;
    private StorageDao storageDao;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userInStorage_isOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test222")
                .setPassword("qwerty")
                .setAge(23)
                .build();
        registrationService.register(user);
        assertNotNull(storageDao.get("test222"));
        assertEquals("test222", storageDao.get("test222").getLogin(),
                "login should match in DB and register");
    }

    @Test
    void register_userLowerSixDigitsLogin_notOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test")
                .setPassword("qwerty")
                .setAge(23)
                .build();
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(user));

    }

    @Test
    void register_userLowerSixDigitsPassword_notOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test123")
                .setPassword("123")
                .setAge(23)
                .build();
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLowerEighteenAge_notOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test123")
                .setPassword("123123")
                .setAge(17)
                .build();
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userWithEdgeAge_isOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test123")
                .setPassword("1231231")
                .setAge(18)
                .build();
        registrationService.register(user);
        assertNotNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_userWithEdgePassword_isOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test1235")
                .setPassword("123123")
                .setAge(19)
                .build();
        registrationService.register(user);
        assertNotNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_userWithEdgeLogin_isOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test12")
                .setPassword("1231231")
                .setAge(19)
                .build();
        registrationService.register(user);
        assertNotNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_userNullIdExpression_notOk() {
        user = new User.UserBuilder().setLogin("test123")
                .setPassword("123333")
                .setAge(18)
                .build();
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userNullLoginExpression_notOk() {
        user = new User.UserBuilder().setId(312313L)
                .setPassword("123456")
                .setAge(18)
                .build();
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userNullPasswordExpression_notOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test123")
                .setAge(18)
                .build();
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userNullAgeExpression_notOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test123")
                .setPassword("123123")
                .build();
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userNegativeAge_notOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test123")
                .setPassword("123123")
                .setAge(-1)
                .build();
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userWithTheSameLogin_notOk() {
        user = new User.UserBuilder().setId(312313L)
                .setLogin("test1234")
                .setPassword("123123")
                .setAge(19)
                .build();
        User sameUser = new User.UserBuilder().setId(312L)
                .setLogin("test1234")
                .setPassword("123123123")
                .setAge(19)
                .build();
        registrationService.register(user);
        assertThrows(IllegalDataException.class,
                () -> registrationService.register(sameUser));
    }
}
