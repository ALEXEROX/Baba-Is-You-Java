package Rules;

import GameObjects.Direction;
import GameObjects.Position;
import GameObjects.Subject;

public abstract class Feature extends Operand{

    public abstract void action(Subject subject, Direction direction);
    public abstract  void interaction(Subject first, Subject second, Direction direction);
}
