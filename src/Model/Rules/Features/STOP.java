package Model.Rules.Features;

import Model.GameObjects.*;
import Model.Rules.*;

public class STOP extends Feature {

    public STOP(){
        word = "STOP";
    }

    @Override
    public void action(GameObject subject, Direction direction) {

    }

    @Override
    public void interaction(GameObject first, GameObject second, Direction direction) {
        second.cancelMove();
    }
}
