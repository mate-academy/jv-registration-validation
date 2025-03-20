package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationService service = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        people.clear();
    }

    @Test
    void registerUser_Ok() {
        User first = new User("CommonLogin","CommonPassword",20);
        User second = new User("LoginOk","PasswordOk",32);
        User third = new User("TheNormalLogin","GoodPassword",22);
        service.register(first);
        service.register(second);
        service.register(third);
        assertEquals(people.size(),3,"The length of the storage should be 3");
    }

    @Test
    void registerNullUser_notOk() {
        User nullUser = null;
        assertThrows(InvalidDataException.class,() -> {
            service.register(nullUser);
        });
    }

    @Test
    void registerSameUser_notOk() {
        User user = new User("CommonLogin","CommonPass",20);
        service.register(user);
        assertThrows(InvalidDataException.class,() -> {
            service.register(user);
        });
    }

    @Test
    void registerUserWithInvalidData_notOk() {
        User first = new User(null,null,null);
        User second = new User("GoodLogin",null,30);
        User third = new User(null,"PassGood",20);
        assertThrows(InvalidDataException.class,() -> {
            service.register(first);
        });
        assertThrows(InvalidDataException.class,() -> {
            service.register(second);
        });
        assertThrows(InvalidDataException.class,() -> {
            service.register(third);
        });
    }

    @Test
    void registerUserCredentialsTooShort_notOk() {
        User first = new User("User","Pass",20);
        assertThrows(InvalidDataException.class,() -> {
            service.register(first);
        });
    }

    @Test
    void registerUserAgeInvalid_notOk() {
        User first = new User("GoodLogin","GoodPass",10);
        User second = new User("LoginOk","PassOk",null);
        assertThrows(InvalidDataException.class,() -> {
            service.register(first);
        });
        assertThrows(InvalidDataException.class,() -> {
            service.register(second);
        });
    }
}
