package Rules.Features;

import GameObjects.*;
import Rules.*;

import java.util.List;

public class OPEN extends Feature {

    public OPEN(){
        _word = "OPEN";
    }

    @Override
    public void action(GameObject subject, Direction direction) {

    }

    @Override
    public void interaction(GameObject first, GameObject second, Direction direction) {
        if(second.hasFeature(SHUT.class)){
            first.destroy();
            second.destroy();
        }
    }
}
