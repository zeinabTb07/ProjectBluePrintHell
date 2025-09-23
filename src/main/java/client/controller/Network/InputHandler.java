package client.controller.Network;

import java.io.PrintWriter;
import java.util.Scanner;

public class InputHandler implements Runnable {
    private final PrintWriter out;

    public InputHandler(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = scanner.nextLine();
            out.println(msg);
        }
    }
}
