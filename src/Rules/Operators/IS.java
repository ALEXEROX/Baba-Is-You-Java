package Rules.Operators;

import Core.*;
import GameObjects.*;
import Rules.*;

import java.util.*;

public class IS extends Operator {

    public IS(){
        _word = "IS";
    }

    @Override
    public boolean canInteract(Operand left, Operand right) {
        return left.getClass() == SubjectName.class;
    }

    @Override
    public void action(Level level, Operand left, Operand right) {
        List<GameObject> gameObjects = level.getGameObjects();

        for(GameObject gameObject: gameObjects) {
            if(gameObject.getName().equals(left.getText())) {
                if (right.getClass() == SubjectName.class) {
                    gameObject.transform(right.getText());
                } else {
                    gameObject.addFeature((Feature) right);
                }
            }
        }
    }
}
