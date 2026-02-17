package Rules.Features;

import GameObjects.Direction;
import GameObjects.Subject;
import Rules.*;

public class STOP extends Feature {

    public STOP(){
        _word = "STOP";
    }
    @Override
    public void action(Subject first, Subject second, Direction direction) {

    }
}
