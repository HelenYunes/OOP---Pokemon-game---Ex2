package gameClient;

import gameClient.GUI.MyFrame;

import java.io.IOException;

public class Ex2 {
    public static int id = 0;
    public static int level = 0;

    public static void main(String[] args) throws IOException {
        MyFrame frame;
        if (args.length == 0) {
            frame = new MyFrame();
        } else {
            id = Integer.parseInt(args[0]);
            level = Integer.parseInt(args[1]);
            frame = new MyFrame(id, level);
        }
        frame.show();
    }
}
