package by.Roman191976.Quizer;

/**
 * Задание с заранее заготовленным текстом. 
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    private String text;
    private String answer;

    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    TextTask(
        String text,
        String answer
    ) {
            this.text = text;
            this.answer = answer;
        }
    
        @Override
        public String getText() {
            return text;
        }
    
        @Override
        public Result validate(String userAnswer) {
            if (userAnswer.isEmpty()) return Result.INCORRECT_INPUT;
            if (answer.equalsIgnoreCase(userAnswer)) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        }
    }

