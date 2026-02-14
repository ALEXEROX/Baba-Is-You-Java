package Rules.Operators;

import GameObjects.Subject;
import Rules.Feature;
import Rules.Operand;
import Rules.Operator;
import Rules.SubjectName;

public class IS extends Operator {

    public IS(){
        _word = "IS";
    }

    @Override
    public boolean canInteract(Operand left, Operand right) {
        return left.getClass() == SubjectName.class;
    }

    @Override
    public void action(Subject subject, Operand right) {
        if(right.getClass() == SubjectName.class){

        }
        else
        {

        }
    }
}
