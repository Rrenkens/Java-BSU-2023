package by.aadeglmmy.quizer;

public interface Task {

  String getText();

  Result validate(String answer);
}
