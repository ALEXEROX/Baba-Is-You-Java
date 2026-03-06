package Model.Rules.Features;

import Model.GameObjects.*;
import Model.Rules.*;

public class YOU extends Feature {

    public YOU(){
        word = "YOU";
    }

    @Override
    public void performActionOnGameObject(GameObject subject, Direction direction) {
        subject.prepareMove(direction);
        subject.addFeature(new PUSH());
    }

    @Override
    public void performInteractionBetweenGameObjects(GameObject first, GameObject second, Direction direction) {

    }
}
