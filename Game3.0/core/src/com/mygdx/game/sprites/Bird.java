package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

public class Bird {
    private static int GRAVITY = -10;
    private static final int movement  = 100;
    private Vector3 position;
    private Vector3 velocity;

    private Rectangle areaBird;
    private BirdAnimation animation;
    private Texture texture;

    public Bird(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("birdanimation.png");
        animation = new BirdAnimation(new TextureRegion(texture), 3, 0.5f);
        areaBird = new Rectangle(x, y, texture.getWidth()/3, texture.getHeight());
    }

    public void update(float dt){
        animation.update(dt);
        if(position.y>0)
            velocity.add(0,GRAVITY,0);
        velocity.scl(dt);
        position.add(movement*dt,velocity.y,0);
        velocity.scl(1/dt);
        if(position.y<0) {
            position.y=0;
        }
        if(position.y> MyGdxGame.HEIGHT/2 - texture.getHeight()/2) {
            position.y = MyGdxGame.HEIGHT/2 - texture.getHeight()/2;
        }
        areaBird.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void jump(){
        velocity.y=200;
    }


    public Rectangle getAreaBird() {
        return areaBird;
    }

    public void dispose(){
        texture.dispose();
    }

    public TextureRegion getBird(){
        return animation.getFrame();
    }
}
