package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static final User[] sampleUsers = new User[2];

    public HelloWorldTest() {
        User u1 = new User();
        u1.setAge(22);
        u1.setPassword("wwwrrrt");
        u1.setLogin("asasas");
        u1.setId(113l);
        sampleUsers[0] = u1;

        User u2 = new User();
        u2.setAge(98);
        u2.setPassword("123hhh");
        u2.setLogin("123asasas");
        u2.setId(123l);
        sampleUsers[1] = u2;

    }

    @Test
    public void getStorageDaoImpl_login() {
        Storage.people.clear();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        for (int i = 0; i < sampleUsers.length; i++) {
            storageDao.add(sampleUsers[i]);
            String actualResult = storageDao.get(sampleUsers[i].getLogin()).getLogin();
            String expectedResult = sampleUsers[i].getLogin();

            Assert.assertEquals(
                    "Test1 failed "
                            + "\nactual: \n" + actualResult
                            + "\nexpected: \n" + expectedResult,
                    expectedResult,
                    actualResult);
        }
    }
    @Test
    public void getStorageDaoImpl_size() {
        Storage.people.clear();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        for (int i = 0; i < sampleUsers.length; i++) {
            storageDao.add(sampleUsers[i]);
        }
        int actualSize = Storage.people.size();
        int expectedSize = sampleUsers.length;
        Assert.assertEquals(
                "Test2 failed "
                        + "\nactual: \n" + actualSize
                        + "\nexpected: \n" + expectedSize,
                actualSize,
                expectedSize);
    }
}
