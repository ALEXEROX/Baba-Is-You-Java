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
        new TextBlock(new SubjectName("BABA"), level, new Position(2, 1));
        new TextBlock(new IS(), level, new Position(3, 1));
        new TextBlock(new YOU(), level, new Position(4, 1));

        // WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(11, 1));
        new TextBlock(new IS(), level, new Position(12, 1));
        new TextBlock(new STOP(), level, new Position(13, 1));

        // ROCK IS PUSH
        new TextBlock(new SubjectName("ROCK"), level, new Position(2, 8));
        new TextBlock(new IS(), level, new Position(3, 8));
        new TextBlock(new PUSH(), level, new Position(4, 8));

        // FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(11, 8));
        new TextBlock(new IS(), level, new Position(12, 8));
        new TextBlock(new WIN(), level, new Position(13, 8));

        // Стены
        for(int i = 2; i < 14; i++){
            new Subject("WALL", level, new Position(i, 2));
            new Subject("WALL", level, new Position(i, 7));
        }

        // Камни
        for(int i = 3; i < 7; i++){
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
        Level level = new Level(13, 7, "LEVEL_3");

        // BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(1, 1));
        new TextBlock(new IS(), level, new Position(2, 1));
        new TextBlock(new YOU(), level, new Position(3, 1));

        // WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(5, 1));
        new TextBlock(new IS(), level, new Position(6, 1));
        new TextBlock(new STOP(), level, new Position(7, 1));

        // KEY IS PUSH
        new TextBlock(new SubjectName("KEY"), level, new Position(9, 1));
        new TextBlock(new IS(), level, new Position(10, 1));
        new TextBlock(new PUSH(), level, new Position(11, 1));

        // KEY IS OPEN
        new TextBlock(new SubjectName("KEY"), level, new Position(1, 5));
        new TextBlock(new IS(), level, new Position(2, 5));
        new TextBlock(new OPEN(), level, new Position(3, 5));

        // DOOR IS SHUT
        new TextBlock(new SubjectName("DOOR"), level, new Position(5, 5));
        new TextBlock(new IS(), level, new Position(6, 5));
        new TextBlock(new SHUT(), level, new Position(7, 5));

        // FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(9, 5));
        new TextBlock(new IS(), level, new Position(10, 5));
        new TextBlock(new WIN(), level, new Position(11, 5));

        new Subject("BABA", level,new Position(3, 3));
        new Subject("KEY", level,new Position(5, 3));
        new Subject("DOOR", level,new Position(7, 3));
        new Subject("FLAG", level,new Position(9, 3));

        return level;
    }
}
