package Rules.Features;

import GameObjects.Direction;
import GameObjects.GameObject;
import GameObjects.Subject;
import Rules.*;

public class WIN extends Feature {

    public WIN(){
        _word = "WIN";
    }

    @Override
    public void action(GameObject subject, Direction direction) {

    }

    @Override
    public void interaction(GameObject first, GameObject second, Direction direction) {

    }
}
