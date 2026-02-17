package Rules.Features;

import GameObjects.*;
import Rules.*;

public class YOU extends Feature {

    public YOU(){
        _word = "YOU";
    }

    @Override
    public void action(Subject first, Subject second, Direction direction) {
        first.prepareMove(direction);
    }
}
