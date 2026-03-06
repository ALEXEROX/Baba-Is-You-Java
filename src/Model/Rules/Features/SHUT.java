package Model.Rules.Features;

import Model.GameObjects.*;
import Model.Rules.*;

public class SHUT extends Feature {

    public SHUT(){
        word = "SHUT";
    }

    @Override
    public void action(GameObject subject, Direction direction) {

    }

    @Override
    public void interaction(GameObject first, GameObject second, Direction direction) {
        if(!second.hasFeature(OPEN.class)){
            second.cancelMove();
        }
    }
}
