package core.basesyntax;

public class DataNotVaidExeption extends RuntimeException {
    public DataNotVaidExeption(String message) {
        System.out.println(message);
    }
}
