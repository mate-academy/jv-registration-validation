package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class StorageTest {
    @Test
    public void secure_peopleCannotBeModified_Ok() {
        try {
            Field field = Storage.class.getDeclaredField("people");
            field.set(null, new ArrayList<>());
            fail("Capacity of ArrayList people should be final");
        } catch (NoSuchFieldException e) {
            System.out.println("Variable people not found" + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("Variable people is final we cannot change it" + e.getMessage());
        }
    }
}
