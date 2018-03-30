package de.android.ayrathairullin.whackamole.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Mole {
    public enum State {GOINGUP, GOINGDOWN, UNDERGROUND};

    public State state = State.GOINGUP;
    public float currentHeight = 0.0f;
    public float speed = 2.0f;
    public float timeUnderground = 0.0f, maxTimeUnderground = .8f;

    public Sprite moleSprite;
    public Vector2 position = new Vector2();
    public float height, width, scaleFactor;

    public void render(SpriteBatch batch) {
        moleSprite.draw(batch);
    }

    public void update() {
        switch (state) {
            case UNDERGROUND:
                if (timeUnderground >= maxTimeUnderground) {
                    state = State.GOINGUP;
                    timeUnderground = 0.0f;
                }else {
                    timeUnderground += Gdx.graphics.getDeltaTime();
                }
                break;
            case GOINGUP:
                currentHeight += speed;
                if (currentHeight >= height) {
                    currentHeight = height;
                    state = State.GOINGDOWN;
                }
                break;
            case GOINGDOWN:
                currentHeight -= speed;
                if (currentHeight <= 0.0) {
                    currentHeight = 0.0f;
                    state = State.UNDERGROUND;
                }
                break;
        }
        moleSprite.setRegion(0, 0, (int) (width / scaleFactor), (int)(currentHeight / scaleFactor));
        moleSprite.setSize(moleSprite.getWidth(), currentHeight);
    }

    public void randomizeWaitTime() {
        maxTimeUnderground = (float)Math.random() * 2f;
    }

    public boolean handleTouch(float touchX, float touchY) {
        if ((touchX >= position.x) && touchX <= (position.x + width) &&
                (touchY >= position.y) && touchY <= (position.y + currentHeight)) {
            state = State.UNDERGROUND;
            currentHeight = 0.0f;
            moleSprite.setRegion(0, 0, (int)(width / scaleFactor), (int)(currentHeight / scaleFactor));
            moleSprite.setSize(moleSprite.getWidth(), currentHeight);
            timeUnderground = 0.0f;
            randomizeWaitTime();
            return true;
        }
        return false;
    }
}
