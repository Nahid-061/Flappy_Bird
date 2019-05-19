package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BirdAnimation {

    private Array<TextureRegion> frames;
    private float frameShowTime;
    private float currentFrameTime;
    private int framecount;
    private int frame;

    public BirdAnimation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;

        for(int i = 0; i < frameCount; i++){
            frames.add( new TextureRegion(region, i*frameWidth, 0 , frameWidth, region.getRegionHeight()));
            framecount = frameCount;
            frameShowTime = cycleTime / framecount;
            frame = 0;

        }
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > frameShowTime) {
            frame++;
            currentFrameTime = 0;
        }
        if(frame == framecount )
            frame = 0;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
