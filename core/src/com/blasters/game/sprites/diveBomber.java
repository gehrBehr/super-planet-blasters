package com.blasters.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.blasters.game.gameworld.GameWorld;

import java.util.Random;

/**
 * Created by Adam on 12/8/2016.
 * I wanted to make the angle similar to a bullet firing
 * I haven't had enough time to make the angle right. my commented
 * out things show what I was doing.
 */

public class diveBomber extends Fighter {
    public diveBomber(GameWorld world) {
        super(world);
    }

    //Random randomOffset = new Random();
   // float angle;
    private Animation deathAnimation;
    private boolean isDead;
    private float deathTimer;

    public void defineFighter() {
        value = 1;
        health  = 1;
        speed = -480;
        //angle = randomOffset.nextFloat();
        TextureRegion fighter = world.getAtlas().findRegion("draxbomberShip"); //updated to reflect new atlas
        sprite = new Sprite(fighter);
        x = random.nextInt(Gdx.graphics.getWidth() - sprite.getRegionWidth());
        y = random.nextInt(Gdx.graphics.getHeight()) + Gdx.graphics.getHeight();
        sprite.setPosition(x, y);
        sprite.setScale(.5f, .5f);
        setBadGuyDeath();

    }

    public void update(float delta) {
        move(delta);
        if (isDead) {
            deathTimer += delta;
            sprite.setRegion(deathAnimation.getKeyFrame(deathTimer));
            if (deathAnimation.isAnimationFinished(deathTimer))
            {
                world.enemies.removeValue(this, true);
            }
        }

        if (sprite.getY() + sprite.getHeight() < 0) {
            world.enemies.removeValue(this, true);
        }
        else if (sprite.getBoundingRectangle().overlaps(world.player.sprite.getBoundingRectangle())) {
            die();
        }
        for (Bullet bullet : world.bullets) {
            if (sprite.getBoundingRectangle().overlaps(bullet.laserSprite.getBoundingRectangle())) {
                health--;
                //bullet
                bullet.kill();
                if(health <= 0) {
                    die();
                }
            }
        }

    }

    @Override
    public void move(float delta) {
        Sprite s = sprite; //Get current enemy's sprite
        float targetX = Gdx.graphics.getWidth() / 2; //Player's X
        float targetY = -200; //Player's Y
        float spriteX = s.getX(); //Enemy's X
        float spriteY = s.getY(); //Enemy's Y
        float x2 = s.getX(); //Enemy's new X
        float y2 = s.getY(); //Enemy's new Y
        float angle; // We use a triangle to calculate the new trajectory
        angle = (float) Math
                .atan2(targetY - spriteY, targetX - spriteX);
        sprite.setRotation(angle);
        x2 += (float) Math.cos(angle) * 400
                * Gdx.graphics.getDeltaTime();
        y2 += (float) Math.sin(angle) * 400
                * Gdx.graphics.getDeltaTime();
        s.setPosition(x2, y2); //Set enemy's new positions.
        velocity.add(0, speed);
        velocity.scl(delta);

        sprite.translate(velocity.x, velocity.y);
    }

    private void fireBullet() {
        //will not fire just fly toward player
    }

    public void die() {
        isDead = true;
        world.hud.addScore(value);

    }
    public void setBadGuyDeath() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(world.getAtlas().getTextures().first(), i * 103 + 11, 82, 103, 84));
        }

        deathAnimation = new Animation(.1f, frames, Animation.PlayMode.NORMAL);

    }
}
