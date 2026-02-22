package Rules;

import GameObjects.Direction;
import GameObjects.GameObject;
import GameObjects.Position;
import GameObjects.Subject;

public abstract class Feature extends Operand{

    public abstract void action(GameObject subject, Direction direction);
    public abstract  void interaction(GameObject first, GameObject second, Direction direction);
}
