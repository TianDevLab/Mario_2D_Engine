package jade;

public class Window {

    private int width, height;
    private String title;

    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "mario";
    }

    public static Window get() {
        if (window == null) {
            Window.window = new Window();
        }
        return new Window();
    }
}
