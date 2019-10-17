package Task01;

import java.util.Scanner;

public final class Basics {

    private static Tui tui = new Tui();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        do {
            tui.printMenu();
            input = sc.nextLine();
            if(input.equals("exit")) continue;
            tui.input(input);
            System.out.println(input);
        } while (!input.equals("exit"));
    }
}