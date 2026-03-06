package Model.Rules.Features;

import Model.GameObjects.*;
import Model.Rules.*;

public class SHUT extends Feature {

    public SHUT(){
        word = "SHUT";
    }

    @Override
    public void performActionOnGameObject(GameObject subject, Direction direction) {

    }

    @Override
    public void performInteractionBetweenGameObjects(GameObject first, GameObject second, Direction direction) {
        if(!second.hasFeature(OPEN.class)){
            second.cancelMove();
        }
    }
}
