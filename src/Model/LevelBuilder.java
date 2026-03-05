package Model;

import Model.GameObjects.Position;
import Model.GameObjects.Subject;
import Model.GameObjects.TextBlock;
import Model.Rules.Features.*;
import Model.Rules.Operators.IS;
import Model.Rules.SubjectName;

public class LevelBuilder {

    public static Level createLevel1() {
        Level level = new Level(16, 10, "LEVEL_1");

        // BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(13, 0));
        new TextBlock(new IS(), level, new Position(14, 0));
        new TextBlock(new STOP(), level, new Position(15, 0));

        // ROCK IS PUSH
        new TextBlock(new SubjectName("ROCK"), level, new Position(0, 9));
        new TextBlock(new IS(), level, new Position(1, 9));
        new TextBlock(new PUSH(), level, new Position(2, 9));

        // FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(13, 9));
        new TextBlock(new IS(), level, new Position(14, 9));
        new TextBlock(new WIN(), level, new Position(15, 9));

        // Стены
        for(int i = 0; i < 16; i++){
            new Subject("WALL", level, new Position(i, 1));
            new Subject("WALL", level, new Position(i, 8));
        }

        // Камни
        for(int i = 2; i < 8; i++){
            new Subject("ROCK", level, new Position(8, i));
        }

        // BABA
        new Subject("BABA", level, new Position(3, 5));

        // FLAG
        new Subject("FLAG", level, new Position(13, 5));

        return level;
    }
    public static Level createLevel2() {
        Level level = new Level(10, 8, "LEVEL_2");

        // BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(7, 0));
        new TextBlock(new IS(), level, new Position(8, 0));
        new TextBlock(new STOP(), level, new Position(9, 0));

        // ROCK IS DEFEAT
        new TextBlock(new SubjectName("ROCK"), level, new Position(0, 7));
        new TextBlock(new IS(), level, new Position(1, 7));
        new TextBlock(new DEFEAT(), level, new Position(2, 7));

        // FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(7, 7));
        new TextBlock(new IS(), level, new Position(8, 7));
        new TextBlock(new WIN(), level, new Position(9, 7));

        // Стены по краям
        for(int i = 0; i < 10; i++){
            new Subject("WALL", level, new Position(i, 1));
            new Subject("WALL", level, new Position(i, 6));
        }

        // BABA
        new Subject("BABA", level, new Position(1, 3));

        // ROCK
        new Subject("ROCK", level, new Position(4, 3));

        // FLAG
        new Subject("FLAG", level, new Position(8, 3));

        return level;
    }
    public static Level createLevel3() {
        Level level = new Level(12, 10, "LEVEL_3");

        // BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(9, 0));
        new TextBlock(new IS(), level, new Position(10, 0));
        new TextBlock(new STOP(), level, new Position(11, 0));

        // KEY IS PUSH
        new TextBlock(new SubjectName("KEY"), level, new Position(4, 0));
        new TextBlock(new IS(), level, new Position(5, 0));
        new TextBlock(new PUSH(), level, new Position(6, 0));

        // KEY IS OPEN
        new TextBlock(new SubjectName("KEY"), level, new Position(0, 9));
        new TextBlock(new IS(), level, new Position(1, 9));
        new TextBlock(new OPEN(), level, new Position(2, 9));

        // DOOR IS SHUT
        new TextBlock(new SubjectName("DOOR"), level, new Position(4, 9));
        new TextBlock(new IS(), level, new Position(5, 9));
        new TextBlock(new SHUT(), level, new Position(6, 9));

        // FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(9, 9));
        new TextBlock(new IS(), level, new Position(10, 9));
        new TextBlock(new WIN(), level, new Position(11, 9));

        new Subject("BABA", level,new Position(3, 6));
        new Subject("KEY", level,new Position(5, 6));
        new Subject("DOOR", level,new Position(7, 6));
        new Subject("FLAG", level,new Position(9, 6));

        return level;
    }
}
