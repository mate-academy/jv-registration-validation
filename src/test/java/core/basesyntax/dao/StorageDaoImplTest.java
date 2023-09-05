package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();

    @Test
    void add_isUserNull_notOkay() {
        User user = null;
        assertThrows(NullPointerException.class, () -> STORAGE_DAO.add(user),
                "Test failed! Method can't take null as user");
    }

    @Test
    void add_returnCorrectUser_okay() {
        User expected = new User("kreeper2004", "ilovejava", 19);
        User actual = STORAGE_DAO.add(expected);
        assertEquals(expected, actual, "Test failed! Method shouldn't change the input data!\n"
                + "Expected: " + expected.toString() + "\n Actual: " + actual.toString());
    }

    @Test
    void get_loginNull_notOkay() {
        User user = new User(null, "1312312", 19);
        assertThrows(NullPointerException.class, () -> STORAGE_DAO.get(user.getLogin()),
                "Test failed! Method can't take null as login");
    }

    @Test
    void add_checkUserExist_okay() {
        User expected = new User("kreeper2004", "ilovejava", 19);
        STORAGE_DAO.add(expected);
        User actual = STORAGE_DAO.get(expected.getLogin());
        assertEquals(expected, actual, "Test failed! Method should return the same user.\n"
                + "Expected: " + expected.toString() + "\n Actual: " + actual.toString());
    }
}
