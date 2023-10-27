package by.waitingsolong.quizer;

/**
 * Enum, который описывает результат ответа на задание
 */
public enum Result {
    OK, // Получен правильный ответ
    WRONG, // Получен неправильный ответ
    INCORRECT_INPUT // Некорректный ввод. Например, текст, когда ожидалось число
}