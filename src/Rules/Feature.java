package Rules;

import GameObjects.Direction;
import GameObjects.Position;
import GameObjects.Subject;

public abstract class Feature extends Operand{

    public abstract void action(Subject first, Subject second, Direction direction);
}
