package se233.chapter4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.model.Character;
import se233.chapter4.model.Keys;
import java.util.ArrayList;

public class Platform extends Pane {
    public static final  int WIDTH = 800 ;
    private Keys keys ;
    public static final int HEIGHT = 400 ;
    public static final int GROUND = 300 ;
    private Image platformImg ;

    private ArrayList<Character> characters ;
    private ArrayList<Score> scoreList ;


    public Platform() {
        this.characters = new ArrayList<>() ;
        this.scoreList = new ArrayList<>() ;

        this.keys = new Keys() ;
        platformImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png")) ;
        ImageView backgroundImg = new ImageView(platformImg) ;
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);
        Character player1 = new Character(100,30,0,0, KeyCode.A , KeyCode.D,KeyCode.W, "MarioSheet",1,1,7,17) ;
        Character player2 = new Character(0,30,0,0, KeyCode.LEFT , KeyCode.RIGHT,KeyCode.UP, "MegamanSheet",1,1,5,19) ;
        characters.add(player1);
        characters.add(player2);

        this.getChildren().addAll(backgroundImg);

        for(Character ch : characters){
            this.getChildren().add(ch);
        }

        scoreList.add(new Score(30 , GROUND + 30));
        scoreList.add(new Score(Platform.WIDTH - 60 , GROUND + 30));
        scoreList.forEach( list -> { this.getChildren().add(list) ; });

    }

    public ArrayList<Character> getCharacters() {return  this.characters ; }

    public ArrayList<Score> getScoreList() {
        return scoreList;
    }

    public Keys getKeys() {
        return keys ;
    }
}