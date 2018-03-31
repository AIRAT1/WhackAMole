package de.android.ayrathairullin.whackamole.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.android.ayrathairullin.whackamole.managers.GameManager;

public class Mole {
    public enum State {GOINGUP, GOINGDOWN, UNDERGROUND, STUNNED}

    public State state = State.GOINGUP;
    public float currentHeight = 0.0f;
    public float speed = 4.0f; // TODO change standard value 2.0f
    public float timeUnderground = 0.0f, maxTimeUnderground = .8f;

    public Sprite moleSprite, stunSprite;
    public Vector2 position = new Vector2();
    public float height, width, scaleFactor;
    public float stunTime = .1f;
    public float stunCounter = 0.0f;

    public void render(SpriteBatch batch) {
        moleSprite.draw(batch);
        if (state == State.STUNNED) {
            stunSprite.draw(batch);
        }
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
            case STUNNED:
                if (stunCounter >= stunTime) {
                    state = State.UNDERGROUND;
                    stunCounter = 0.0f;
                    currentHeight = 0.0f;
                    randomizeWaitTime();
                }else {
                    stunCounter += Gdx.graphics.getDeltaTime();
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
            stunSprite.setPosition(position.x + width - (stunSprite.getWidth() / 2),
                    position.y + currentHeight - (stunSprite.getHeight() / 2));
            state = State.STUNNED;
            GameManager.hitSound.play(.5f);

//            state = State.UNDERGROUND;
//            currentHeight = 0.0f;
//            moleSprite.setRegion(0, 0, (int)(width / scaleFactor), (int)(currentHeight / scaleFactor));
//            moleSprite.setSize(moleSprite.getWidth(), currentHeight);
//            timeUnderground = 0.0f;
//            randomizeWaitTime();
            return true;
        }
        return false;
    }
}
