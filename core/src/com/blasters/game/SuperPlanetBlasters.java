package com.blasters.game;


import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blasters.game.screens.GameScreen;
import com.blasters.game.screens.MenuScreen;

/*
 * SUPERPLANETBLASTERS
 * Our game. It extends the game class so that it will render and dispose everything that we need
 * it to. It sets screens. We pass this to the screens so that all the screens can have a spritebatch.
 * Also, we need to pass the game class to our screens so that our screens can set other screens.
 * Also, we should define our width and height here instead of using Gdx.graphics.getWidth and
 * Height all the time. That would give us much cleaner code.
 */
public class SuperPlanetBlasters extends Game {
	public static final int HEIGHT = 800;
	public static final int WIDTH =  600;
	public static final String TITLE = "Super Planet Blasters!";
	public SpriteBatch sb;
	//currently this just sets the menu screen
	@Override
	public void create () {
		sb = new SpriteBatch();
		setScreen(new MenuScreen(this));
		Gdx.app.log("SPB", "Created");
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		sb.dispose();
		super.dispose();
	}
}
