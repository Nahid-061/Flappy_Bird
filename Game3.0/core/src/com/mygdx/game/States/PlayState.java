package com.mygdx.game.States;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

import java.lang.reflect.*;
import  java.util.*;
public class PlayState extends State {
    private static final int tube_spacing = 150;
    private static final int tube_count = 4;
    private static final int groundYOffSet = -50;

    private Array<Tube> tubes;

    private Bird bird;
    private Texture background;
    private Tube tube;

    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    public  PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, MyGdxGame.WIDTH/2,MyGdxGame.HEIGHT/2);

        bird = new Bird(50,250);
        background = new Texture("background-day.png");

        ground = new Texture("base.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2, groundYOffSet);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + ground.getWidth(), groundYOffSet);

        tubes = new Array<Tube>();

        for(int i = 0; i< 4; i++){
            tubes.add(new Tube(50 + (i+1) * (tube_spacing + Tube.tube_width)));
        }



    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x+80;

        for(Tube tube : tubes){
            if(tube.getPosUpTube().x < cam.position.x - cam.viewportWidth/2 - tube.getUpTube().getWidth())
                tube.reposition(tube.getPosUpTube().x + (Tube.tube_width + tube_spacing) * tube_count);
        }

        for(Tube tube : tubes){
            if(tube.collides(bird.getAreaBird()))
                gsm.set(new PlayState(gsm));
        }

        if(bird.getPosition().y <= ground.getHeight() + groundYOffSet)
            gsm.set(new PlayState(gsm));
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,cam.position.x-cam.viewportWidth/2,0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes){
            sb.draw(tube.getUpTube(), tube.getPosUpTube().x , tube.getPosUpTube().y);
            sb.draw(tube.getDownTube(), tube.getPosDownTube().x , tube.getPosDownTube().y);
        }


        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        for(Tube tube : tubes){
            tube.dispose();
        }
        /*tube2.dispose();
        tube3.dispose();
        tube1.dispose();
        tube4.dispose();*/
        //System.out.println("Play State dispose");
    }

    private void updateGround(){
        if(cam.position.x - cam.viewportWidth/2 > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(cam.position.x - cam.viewportWidth/2 > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }

}
