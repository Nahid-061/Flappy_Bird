package com.mygdx.game.States;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

import java.lang.reflect.*;
import  java.util.*;
public class PlayState extends State {
    private static final int tube_spacing = 150;
    private static final int tube_count = 4;
    private static final int groundYOffSet = -50;

    //private Array <> tubes;
    private Tube[] tubes;

    private Bird bird;
    private Texture background;
    private Tube tube1, tube2, tube3, tube4;

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


        tube1 = new Tube( 50+tube_spacing + Tube.tube_width);
        tube2 = new Tube(2*(tube_spacing + Tube.tube_width));
        tube3 = new Tube( 3*(tube_spacing + Tube.tube_width));
        tube4 = new Tube(4*(tube_spacing + Tube.tube_width));


        //tubes = new Tube[tube_count];
        /*for(int i=1;i<=tube_count;i++){
            tubes[i] = new Tube(i*(tube_spacing+Tube.tube_width));
        }*/
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

        if(tube1.getPosUpTube().x < cam.position.x - cam.viewportWidth/2 - tube1.getUpTube().getWidth())
                tube1.reposition(tube1.getPosUpTube().x + (Tube.tube_width + tube_spacing) * tube_count);
        if(tube2.getPosUpTube().x < cam.position.x - cam.viewportWidth/2 - tube2.getUpTube().getWidth())
            tube2.reposition(tube2.getPosUpTube().x + (Tube.tube_width + tube_spacing) * tube_count);
        if(tube3.getPosUpTube().x < cam.position.x - cam.viewportWidth/2 - tube3.getUpTube().getWidth())
            tube3.reposition(tube3.getPosUpTube().x + (Tube.tube_width + tube_spacing) * tube_count);
        if(tube4.getPosUpTube().x < cam.position.x - cam.viewportWidth/2 - tube4.getUpTube().getWidth())
            tube4.reposition(tube4.getPosUpTube().x + (Tube.tube_width + tube_spacing) * tube_count);

        if(tube1.collides(bird.getAreaBird()))
            gsm.set(new PlayState(gsm));
        else if(tube2.collides(bird.getAreaBird()))
            gsm.set(new PlayState(gsm));
        else if(tube3.collides(bird.getAreaBird()))
            gsm.set(new PlayState(gsm));
        else if(tube4.collides(bird.getAreaBird()))
            gsm.set(new PlayState(gsm));

        if(bird.getPosition().y <= ground.getHeight() + groundYOffSet)
            gsm.set(new PlayState(gsm));
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,cam.position.x-cam.viewportWidth/2,0);
        sb.draw(bird.getBird(),bird.getPosition().x, bird.getPosition().y);
        sb.draw(tube1.getUpTube(), tube1.getPosUpTube().x , tube1.getPosUpTube().y);
        sb.draw(tube1.getDownTube(), tube1.getPosDownTube().x , tube1.getPosDownTube().y);
        sb.draw(tube2.getUpTube(), tube2.getPosUpTube().x , tube2.getPosUpTube().y);
        sb.draw(tube2.getDownTube(), tube2.getPosDownTube().x , tube2.getPosDownTube().y);
        sb.draw(tube3.getUpTube(), tube3.getPosUpTube().x , tube3.getPosUpTube().y);
        sb.draw(tube3.getDownTube(), tube3.getPosDownTube().x , tube3.getPosDownTube().y);
        sb.draw(tube4.getUpTube(), tube4.getPosUpTube().x , tube4.getPosUpTube().y);
        sb.draw(tube4.getDownTube(), tube4.getPosDownTube().x , tube4.getPosDownTube().y);

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        tube2.dispose();
        tube3.dispose();
        tube1.dispose();
        tube4.dispose();
        System.out.println("Play State dispose");
    }

    private void updateGround(){
        if(cam.position.x - cam.viewportWidth/2 > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(cam.position.x - cam.viewportWidth/2 > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }

}
