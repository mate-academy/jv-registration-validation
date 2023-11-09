package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final String lessThan6Chars = "oops";
    private final String over6Chars = "misterProper";
    private final int minAge = 18;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User testNullUser = new User();
    private User testUser2 = new User();
    private StorageDao storageDao = new StorageDaoImpl();

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        }, "User cannot be null");
    }

    @Test
    public void register_nullLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Login cannot be null");
    }

    @Test
    public void register_shortLogin_notOk() {
        testNullUser.setLogin(lessThan6Chars);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Login must be at least 6 characters");
    }

    @Test
    public void register_nullPassword_notOk() {
        testNullUser.setLogin(over6Chars);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Password cannot be null");
    }

    @Test
    public void register_shortPassword_notOk() {
        testNullUser.setLogin(over6Chars);
        testNullUser.setPassword(lessThan6Chars);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Password must be at least 6 characters");
    }

    @Test
    public void register_lessThanMinimalAge_notOk() {
        testNullUser.setLogin(over6Chars);
        testNullUser.setPassword(over6Chars);
        testNullUser.setAge(9);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Min allowed age is " + minAge);
    }

    @Test
    public void register_loginIsTaken_notOk() {
        testNullUser.setLogin(over6Chars);
        testNullUser.setPassword(over6Chars);
        testNullUser.setAge(19);
        try {
            registrationService.register(testNullUser);
        } catch (RegistrationException e) {
            System.out.println(e);
        }
        testUser2.setLogin(over6Chars);
        testUser2.setPassword(over6Chars);
        testUser2.setAge(19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser2);
        }, " This login was already taken");
    }

    @Test
    public void register_successfulAdding_OK() {
        testNullUser.setLogin(over6Chars);
        testNullUser.setPassword(over6Chars);
        testNullUser.setAge(19);
        try {
            registrationService.register(testNullUser);
        } catch (RegistrationException e) {
            System.out.println(e);
        }
        assertEquals(testNullUser, storageDao.get(testNullUser.getLogin()));
    }
}
