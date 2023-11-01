package by.katierevinska.quizer;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public interface TaskGenerator {
    /**
     * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
     *
     * @return задание
     * @see    Task
     */

    Task generate() throws Exception;
}

