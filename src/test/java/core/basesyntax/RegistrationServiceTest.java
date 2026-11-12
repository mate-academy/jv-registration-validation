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
    private static final int INVALID_AGE_EDGE = 17;
    private static final int VALID_AGE_EDGE = 18;
    private static final int VALID_AGE = 35;
    private static final int INVALID_AGE = 12;
    private static final int INVALID_AGE_TWO= -12;
    private static final String VALID_LOGIN_EDGE = "abcdef";
    private static final String VALID_LOGIN = "abcdefgh";
    private static final String VALID_LOGIN_TWO = "abcdefghi";
    private static final String VALID_LOGIN_THREE = "abcdefghij";
    private static final String VALID_LOGIN_FOUR = "abcdefghijk";
    private static final String INVALID_LOGIN_EDGE = "abcde";
    private static final String INVALID_LOGIN = "abc";
    private static final String VALID_PASSWORD_EDGE = "123456";
    private static final String VALID_PASSWORD = "12345678";
    private static final String INVALID_PASSWORD = "123";
    private static final String INVALID_PASSWORD_EDGE = "12345";
    private static final String EMPTY_STRING = "";
    
    
    
    private RegistrationServiceImpl registrationServiceImpl;
    
    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationServiceImpl = new RegistrationServiceImpl();
    }
    
    @Test
    void register_userLoginExists_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        Storage.people.add(user);
        
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }
    
    @Test
    void register_invalidUserLogin_notOk() {
        User user = new User();
        user.setLogin(INVALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin(INVALID_LOGIN_EDGE);
        userTwo.setPassword(VALID_PASSWORD);
        userTwo.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userTwo));
        
        User userThree = new User();
        userThree.setLogin(EMPTY_STRING);
        userThree.setPassword(VALID_PASSWORD);
        userThree.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userThree));
        
        User userFour = new User();
        userFour.setLogin(null);
        userFour.setPassword(VALID_PASSWORD);
        userFour.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class, ()
                -> registrationServiceImpl.register(userFour));
    }
    
    @Test
    void register_validUserLogin_Ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN_EDGE);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertEquals(user, registrationServiceImpl.register(user));
        assertEquals(user, Storage.people.get(0));
        
        User userTwo = new User();
        userTwo.setLogin(VALID_LOGIN);
        userTwo.setPassword(VALID_PASSWORD);
        userTwo.setAge(VALID_AGE);
        assertEquals(userTwo, registrationServiceImpl.register(userTwo));
        assertEquals(userTwo, Storage.people.get(1));
    }
    
    @Test
    void register_invalidUserPassword_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin(VALID_LOGIN_TWO);
        userTwo.setPassword(INVALID_PASSWORD_EDGE);
        userTwo.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userTwo));
        
        User userThree = new User();
        userThree.setLogin(VALID_LOGIN_THREE);
        userThree.setPassword(EMPTY_STRING);
        userThree.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userThree));
        
        User userFour = new User();
        userFour.setLogin(VALID_LOGIN_FOUR);
        userFour.setPassword(null);
        userFour.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userFour));
    }
    
    @Test
    void register_validUserPassword_Ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD_EDGE);
        user.setAge(VALID_AGE);
        assertEquals(user, registrationServiceImpl.register(user));
        assertEquals(user, Storage.people.get(0));
        
        User userTwo = new User();
        userTwo.setLogin(VALID_LOGIN_TWO);
        userTwo.setPassword(VALID_PASSWORD);
        userTwo.setAge(VALID_AGE);
        assertEquals(userTwo, registrationServiceImpl.register(userTwo));
        assertEquals(userTwo, Storage.people.get(1));
    }
    
    @Test
    void register_invalidUserAge_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        
        User userTwo = new User();
        userTwo.setLogin(VALID_LOGIN_TWO);
        userTwo.setPassword(VALID_PASSWORD);
        userTwo.setAge(INVALID_AGE_TWO);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userTwo));
        
        User userThree = new User();
        userThree.setLogin(VALID_LOGIN_THREE);
        userThree.setPassword(VALID_PASSWORD);
        userThree.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userThree));
        
        User userFour = new User();
        userFour.setLogin(VALID_LOGIN_FOUR);
        userFour.setPassword(VALID_PASSWORD);
        userFour.setAge(INVALID_AGE_EDGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(userFour));
    }
    
    @Test
    void register_validUserAge_Ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE_EDGE);
        assertEquals(user, registrationServiceImpl.register(user));
        assertEquals(user, Storage.people.get(0));
        
        User userTwo = new User();
        userTwo.setLogin(VALID_LOGIN_TWO);
        userTwo.setPassword(VALID_PASSWORD);
        userTwo.setAge(35);
        assertEquals(userTwo, registrationServiceImpl.register(userTwo));
        assertEquals(userTwo, Storage.people.get(1));
    }
}
