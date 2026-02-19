package Rules.Features;

import GameObjects.Direction;
import GameObjects.Subject;
import Rules.*;

public class WIN extends Feature {

    public WIN(){
        _word = "WIN";
    }

    @Override
    public void action(Subject subject, Direction direction) {

    }

    @Override
    public void interaction(Subject first, Subject second, Direction direction) {

    }
}
