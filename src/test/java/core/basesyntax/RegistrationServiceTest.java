package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationServiceImpl registrationServiceImpl;
    
    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationServiceImpl = new RegistrationServiceImpl();
    }
    
    @Test
    void register_userLogionExists_notOk() {
        User user = new User();
        user.setLogin("hello123");
        user.setAge(20);
        user.setPassword("1234567");
        Storage.people.add(user);
        
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }
    
    @Test
    void register_invalidUserLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("12345678");
        user.setAge(20);
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin("abcde");
        userTwo.setPassword("12345678");
        userTwo.setAge(20);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userTwo));
        
        User userThree = new User();
        userThree.setLogin("");
        userThree.setPassword("12345678");
        userThree.setAge(20);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userThree));
        
        User userFour = new User();
        userFour.setLogin(null);
        userFour.setPassword("12345678");
        userFour.setAge(20);
        assertThrows(InvalidUserDataException.class, ()
                -> registrationServiceImpl.register(userFour));
    }
    
    @Test
    void register_validUserLogin_Ok() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("12345678");
        user.setAge(20);
        assertEquals(user, registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin("abcdefghjklmnop");
        userTwo.setPassword("12345678");
        userTwo.setAge(20);
        assertEquals(userTwo, registrationServiceImpl.register(userTwo));
    }
    
    @Test
    void register_invalidUserPassword_notOk() {
        User user = new User();
        user.setLogin("abcdefg");
        user.setPassword("123");
        user.setAge(20);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin("abcdefgh");
        userTwo.setPassword("12345");
        userTwo.setAge(20);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userTwo));
        
        User userThree = new User();
        userThree.setLogin("abcdefghi");
        userThree.setPassword("");
        userThree.setAge(20);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userThree));
        
        User userFour = new User();
        userFour.setLogin("abcdefghij");
        userFour.setPassword(null);
        userFour.setAge(20);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userFour));
    }
    
    @Test
    void register_validUserPassword_Ok() {
        User user = new User();
        user.setLogin("abcdefg");
        user.setPassword("123456");
        user.setAge(20);
        assertEquals(user, registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin("abcdefgh");
        userTwo.setPassword("123456789");
        userTwo.setAge(20);
        assertEquals(userTwo, registrationServiceImpl.register(userTwo));
    }
    
    @Test
    void register_invalidUserAge_notOk() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("12345678");
        user.setAge(12);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin("abcdefg");
        userTwo.setPassword("12345678");
        userTwo.setAge(-12);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userTwo));
        
        User userThree = new User();
        userThree.setLogin("abcdefgh");
        userThree.setPassword("12345678");
        userThree.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userThree));
        
        User userFour = new User();
        userFour.setLogin("abcdefghi");
        userFour.setPassword("12345678");
        userFour.setAge(17);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userFour));
    }
    
    @Test
    void register_validUserAge_Ok() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("12345678");
        user.setAge(18);
        assertEquals(user, registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin("abcdefg");
        userTwo.setPassword("12345678");
        userTwo.setAge(35);
        assertEquals(userTwo, registrationServiceImpl.register(userTwo));
    }
}
