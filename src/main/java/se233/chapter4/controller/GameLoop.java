package se233.chapter4.controller;

import se233.chapter4.model.Character;
import se233.chapter4.view.Platform;
import se233.chapter4.view.Score;
import java.util.ArrayList;

public class GameLoop implements Runnable {

    private Platform platform ;
    private int frameRate ;
    private float interval ;
    private boolean running ;

    public GameLoop(Platform platform){
        this.platform = platform ;
        this.frameRate = 10 ;
        this.interval = 1000.0f / frameRate ;
        this.running = true ;
    }

    private void update(ArrayList<Character> characters){
        for(Character character : characters){
            if(platform.getKeys().isPressed(character.getLeftKey())){
                character.setScaleX(-1);
                character.moveLeft() ;
                character.trace();
            }

            if(platform.getKeys().isPressed(character.getRightKey())){
                character.setScaleX(1);
                character.moveRight() ;
                character.trace();
            }

            if(!platform.getKeys().isPressed(character.getLeftKey()) && !platform.getKeys().isPressed(character.getRightKey())){
                character.stop() ;

            }

            if(platform.getKeys().isPressed(character.getLeftKey()) || platform.getKeys().isPressed(character.getRightKey())) {
                character.getImageView().tick();
            }

            if(platform.getKeys().isPressed(character.getUpKey())) {
                character.jump();
            }
        }
    }

    private void updateScore(ArrayList<Score> scoreList , ArrayList<Character> characterList){
        javafx.application.Platform.runLater( () -> {
            for (int i = 0; i < scoreList.size(); i++) {
                scoreList.get(i).setPoint(characterList.get(i).getScore());
            }
        });
    }

    @Override
    public void run() {
        while (running){
            float time = System.currentTimeMillis();
                update(platform.getCharacters());

            updateScore(platform.getScoreList() , platform.getCharacters());

            time = System.currentTimeMillis() - time ;
            if(time < interval){
                try{
                    Thread.sleep((long)(interval-time));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                try{
                    Thread.sleep((long)(interval - (interval%time)));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }
    }
}