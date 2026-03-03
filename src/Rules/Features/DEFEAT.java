package Rules.Features;

import GameObjects.*;
import Rules.*;

import java.util.List;

public class DEFEAT extends Feature {

    public DEFEAT(){
        _word = "DEFEAT";
    }

    @Override
    public void action(GameObject subject, Direction direction) {

    }

    @Override
    public void interaction(GameObject first, GameObject second, Direction direction) {
        if(second.hasFeature(YOU.class)){
            second.destroy();
        }
    }
}
