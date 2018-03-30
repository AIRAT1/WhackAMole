package de.android.ayrathairullin.whackamole.managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import de.android.ayrathairullin.whackamole.gameobjects.Mole;

public class GameManager {
    private static float MOLE_RESIZE_FACTOR = 2500f;
    private static float MOLE_VERT_POSITION_FACTOR = 3f;
    private static float MOLE1_HORIZ_POSITION_FACTOR = 5.8f;
    private static float MOLE2_HORIZ_POSITION_FACTOR = 2.4f;
    private static float MOLE3_HORIZ_POSITION_FACTOR = 1.5f;
    private static float HOLE_RESIZE_FACTOR = 1100f;

    static Array<Mole>moles; // array of the moles
    static Texture moleTexture; // texture image for the mole
    static Texture backgroundTexture; // texture image for background
    static Sprite backgroundSprite; // sprite for background
    static Texture holeTexture; // texture image for background
    static Array<Sprite> holeSprites; // array of hole sprites

    public static void initialize(float width,float height){
        moles = new Array<Mole>();
        moleTexture = new Texture(Gdx.files.internal("data/mole.png"));
        for(int i=0;i<9;i++){
            moles.add(new Mole());
        }

        backgroundTexture = new Texture(Gdx.files.internal("data/ground.jpg"));
        backgroundSprite = new Sprite(backgroundTexture); //set background sprite
        backgroundSprite.setSize(width, height);
        backgroundSprite.setPosition(0,0f);

        holeTexture = new Texture(Gdx.files.internal("data/hole.png"));
        holeSprites = new Array<Sprite>();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Sprite sprite = new Sprite(holeTexture);
                sprite.setSize(sprite.getWidth() * (width / HOLE_RESIZE_FACTOR),
                        sprite.getHeight() * (width / HOLE_RESIZE_FACTOR));
                sprite.setPosition(width * (j + 1) / 4f - sprite.getWidth() / 2, height * (i+1) / 4.4f  - sprite.getHeight());
                holeSprites.add(sprite);
            }
        }


        for(int i=0;i<9;i++){
            Mole mole = moles.get(i);
            Sprite sprite = holeSprites.get(i);
            mole.moleSprite = new Sprite(moleTexture);
            float scaleFactor = width / 4000f;
            mole.scaleFactor = scaleFactor;
            mole.width = mole.moleSprite.getWidth() * (scaleFactor);
            mole.height = mole.moleSprite.getHeight() * (scaleFactor);
            mole.moleSprite.setSize(mole.width, mole.height);

            mole.position.x = (((2 * sprite.getX() + sprite.getWidth()) / 2)  - (mole.moleSprite.getWidth() / 2));
            mole.position.y = (sprite.getY() + sprite.getHeight() / 5f);

            mole.moleSprite.setPosition(mole.position.x, mole.position.y);
        }
    }

    public static void renderGame(SpriteBatch batch){
        backgroundSprite.draw(batch);

        for(Sprite sprite : holeSprites)
            sprite.draw(batch);

        for(Mole mole : moles){
            mole.update();
            mole.render(batch);
        }
    }
    public static void dispose() {
        backgroundTexture.dispose();
        holeTexture.dispose();
        moleTexture.dispose();
    }
}
