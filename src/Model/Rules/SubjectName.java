package Model.Rules;

public class SubjectName extends Operand{

    public SubjectName(String word){
        _word = word;
    }

    @Override
    public boolean isSubjectName(){
        return true;
    }
}
