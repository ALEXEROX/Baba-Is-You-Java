package Model.Rules.Features;

import Model.GameObjects.*;
import Model.Rules.*;

public class OPEN extends Feature {

    public OPEN(){
        word = "OPEN";
    }

    @Override
    public void performActionOnGameObject(GameObject subject, Direction direction) {

    }

    @Override
    public void performInteractionBetweenGameObjects(GameObject first, GameObject second, Direction direction) {
        if(second.hasFeature(SHUT.class)){
            first.destroy();
            second.destroy();
        }
    }
}
