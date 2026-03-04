package Model.Rules.Features;

import Model.GameObjects.*;
import Model.Rules.*;

public class OPEN extends Feature {

    public OPEN(){
        word = "OPEN";
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
