package Rules.Features;

import GameObjects.*;
import Rules.*;

import java.util.List;

public class PUSH extends Feature {

    public PUSH(){
        _word = "PUSH";
    }

    @Override
    public void action(GameObject subject, Direction direction) {

    }

    @Override
    public void interaction(GameObject first, GameObject second, Direction direction) {
        if(!first.canLet(direction)){
            second.cancelMove();
        }
        else{
            first.prepareMove(direction);
        }
    }
}
