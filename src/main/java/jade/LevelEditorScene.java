package jade;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene{

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene() {
        System.out.println("Inside level editor scene");
    }

    @Override
    public void update(double dt) {
        System.out.println((1/dt) + "FPS");

        if(!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)){
            changingScene = true;
        }

        if(changingScene && timeToChangeScene > 0){
            timeToChangeScene -= (float) dt;
            Window.get().r -= (float) (dt * 5.0f);
            Window.get().g -= (float) (dt * 5.0f);
            Window.get().b -= (float) (dt * 5.0f);

        } else if (changingScene) {
            Window.changeScene(1);
        }
    }
}
