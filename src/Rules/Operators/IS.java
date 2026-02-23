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
        return left.isSubjectName();
    }

    @Override
    public void action(Level level, Operand left, Operand right) {
        List<GameObject> gameObjects = level.getGameObjects();
        for(int index = 0; index < gameObjects.size(); index++) {
            if(gameObjects.get(index).getName().equals(left.getText())) {
                if (right.isSubjectName()) {
                    gameObjects.get(index).transform(right.getText());
                    index--;
                } else {
                    gameObjects.get(index).addFeature((Feature) right);
                }
            }
        }
    }
}
