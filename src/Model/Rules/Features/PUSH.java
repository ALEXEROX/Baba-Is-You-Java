package Model.Rules.Features;

import Model.GameObjects.*;
import Model.Rules.*;

public class PUSH extends Feature {

    public PUSH(){
        word = "PUSH";
    }

    @Override
    public void performActionOnGameObject(GameObject subject, Direction direction) {

    }

    @Override
    public void performInteractionBetweenGameObjects(GameObject first, GameObject second, Direction direction) {
        if(second.hasFeature(PUSH.class)) {
            if (!first.canLet(direction)) {
                second.cancelMove();
            } else {
                first.prepareMove(direction);
            }
        }
    }
}
