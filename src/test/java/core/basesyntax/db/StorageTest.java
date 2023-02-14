package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {

    @BeforeEach
    public void clearData() {
        Storage.people.clear();
    }

    @Test
    public void storage_exists_ok() {
        assertNotNull(Storage.people);
    }
}
