package de.android.ayrathairullin.whackamole.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import de.android.ayrathairullin.whackamole.gameobjects.Mole;

public class InputManager {
    static Vector3 temp = new Vector3();

    public static void handleInput(OrthographicCamera camera) {
        if (Gdx.input.justTouched()) {
            temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY = temp.y;

            for (int i = 0; i < GameManager.moles.size; i++) {
                Mole mole = GameManager.moles.get(i);
                if (mole.state != Mole.State.STUNNED && mole.handleTouch(touchX, touchY)) {
                    GameManager.score ++;
                    break;
                }
            }
        }
    }
}
