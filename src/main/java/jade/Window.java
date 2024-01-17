package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;

    private long glfwWindow;

    private static Window window = null;

    private float r, g, b, a;
    private boolean fadeToBlack = false;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "mario";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }

    public static Window get() {
        if (window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // add more configuration for ARM silicon Mac
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
//        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
//        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("failed to create a GLFW Window");
        }

        try {
            glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
            glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
            glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
            glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

    }

    public void loop() {
        double beginTime = Time.getTime();
        double endTime = Time.getTime();

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (fadeToBlack) {
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
            }

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                fadeToBlack = true;
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            double dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
