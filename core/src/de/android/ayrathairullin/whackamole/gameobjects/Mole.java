package de.android.ayrathairullin.whackamole.gameobjects;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Mole {
    public Sprite moleSprite;
    public Vector2 position = new Vector2();
    public float height, width;

    public void render(SpriteBatch batch) {
        moleSprite.draw(batch);
    }
}
