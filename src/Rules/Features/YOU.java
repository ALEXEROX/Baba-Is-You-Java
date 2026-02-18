package Rules.Features;

import GameObjects.*;
import Rules.*;

public class YOU extends Feature {

    public YOU(){
        _word = "YOU";
    }

    @Override
    public void action(Subject subject, Direction direction) {
        subject.prepareMove(direction);
    }

    @Override
    public void interaction(Subject first, Subject second, Direction direction) {

    }
}
