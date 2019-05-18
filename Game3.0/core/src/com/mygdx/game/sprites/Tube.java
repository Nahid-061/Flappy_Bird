package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ParticleControllerInfluencer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import java.awt.*;
import java.util.Random;

public class Tube {
    public static final  int tube_width = 52;
    private static final int limit = 130;
    private static final int tubeGap = 100;
    private static final int lowestOpening = 120;
    private Texture upTube,downTube;
    private Vector2 posUpTube,posDownTube;
    private Random rand;
    private Rectangle areaUp , areaDown;

    public Tube(float x){
        upTube = new Texture("downTube.png");
        downTube = new Texture("upTube.png");

        rand = new Random();
        posUpTube = new Vector2(x, rand.nextInt(limit) + tubeGap + lowestOpening);
        posDownTube = new Vector2(x, posUpTube.y - tubeGap - downTube.getHeight());
        areaUp = new Rectangle(posUpTube.x, posUpTube.y, upTube.getWidth(), upTube.getHeight());
        areaDown = new Rectangle(posDownTube.x, posDownTube.y, downTube.getWidth(), downTube.getHeight());
    }

    public Texture getUpTube() {
        return upTube;
    }

    public Texture getDownTube() {
        return downTube;
    }

    public Vector2 getPosUpTube() {
        return posUpTube;
    }

    public Vector2 getPosDownTube() {
        return posDownTube;
    }

    public void reposition(float x){
        posUpTube.set(x, rand.nextInt(limit)+tubeGap+lowestOpening);
        posDownTube.set(x, posUpTube.y-tubeGap-downTube.getHeight());
        areaUp.setPosition(posUpTube.x, posUpTube.y);
        areaDown.setPosition(posDownTube.x, posDownTube.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(areaDown) || player.overlaps((areaUp));
    }

    public void dispose(){
        upTube.dispose();
        downTube.dispose();
    }
}
