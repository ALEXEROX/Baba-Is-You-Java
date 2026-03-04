package Model.Rules.Features;

import Model.GameObjects.*;
import Model.Rules.*;

public class YOU extends Feature {

    public YOU(){
        _word = "YOU";
    }

    @Override
    public void action(GameObject subject, Direction direction) {
        subject.prepareMove(direction);
        subject.addFeature(new PUSH());
    }

    @Override
    public void interaction(GameObject first, GameObject second, Direction direction) {

    }
}
