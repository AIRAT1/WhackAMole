package de.android.ayrathairullin.whackamole.managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import de.android.ayrathairullin.whackamole.gameobjects.Mole;

public class GameManager {
    private static float MOLE_RESIZE_FACTOR = 2500;
    private static float MOLE_VERT_POSITION_FACTOR = 3;
    private static float MOLE1_HORIZ_POSITION_FACTOR = 5.8f;
    private static float MOLE2_HORIZ_POSITION_FACTOR = 2.4f;
    private static float MOLE3_HORIZ_POSITION_FACTOR = 1.5f;

    static Array<Mole> moles;
    static Texture moleTexture, backgroundTexture;
    static Sprite backgroundSprite;

    public static void initialize(float width, float height) {
        moles = new Array<Mole>();
        moleTexture = new Texture(Gdx.files.internal("data/mole.png"));

        backgroundTexture = new Texture(Gdx.files.internal("data/ground.jpg"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(width, height);
        backgroundSprite.setPosition(0, 0);

        for (int i = 0; i < 3; i++) {
            moles.add(new Mole());
        }
        moles.get(0).position.set(width / MOLE1_HORIZ_POSITION_FACTOR, height / MOLE_VERT_POSITION_FACTOR);
        moles.get(1).position.set(width / MOLE2_HORIZ_POSITION_FACTOR, height / MOLE_VERT_POSITION_FACTOR);
        moles.get(2).position.set(width / MOLE3_HORIZ_POSITION_FACTOR, height / MOLE_VERT_POSITION_FACTOR);

        for (Mole mole : moles) {
            mole.moleSprite = new Sprite(moleTexture);
            mole.width = mole.moleSprite.getWidth() * (width / MOLE_RESIZE_FACTOR);
            mole.height = mole.moleSprite.getHeight() * (width / MOLE_RESIZE_FACTOR);
            mole.moleSprite.setSize(mole.width, mole.height);
            mole.moleSprite.setPosition(mole.position.x, mole.position.y);
        }
    }

    public static void renderGame(SpriteBatch batch) {
        backgroundSprite.draw(batch);
        for (Mole mole : moles) {
            mole.render(batch);
        }
    }

    public static void dispose() {
        moleTexture.dispose();
        backgroundTexture.dispose();
    }
}
