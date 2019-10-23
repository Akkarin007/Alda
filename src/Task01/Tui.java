package Task01;


import java.io.IOException;
import java.util.Objects;

public class Tui {

    ControllerInterface<String, String> controller = new Controller<>();

    public void printMenu() {

        String[] commands = {"create Implementierung", "read [n] Dateiname", "p", "s deutsch", "i deutsch englisch", "r deutsch", "exit"};
        int length = commands[0].length();
        for (String s : commands) {
            if (s.length() > length) {
                length = s.length();
            }
        }
        String[] description = {"Legt ein Task01.Dictionary an. SortedArrayDictionary ist voreingestellt. ",
                "Liest die ersten n Einträge der Datei in das Task01.Dictionary ein. Wird n weggelassen, dann werden alle Einträge eingelesen.",
                "Gibt alle Einträge des Task01.Dictionary in der Konsole aus (print).",
                "Gibt das entsprechende englische Wort aus (search). ",
                "Fügt ein neues Wortpaar in das Task01.Dictionary ein (insert).",
                "Löscht einen Eintrag (remove).",
                "beendet das Programm."};

        for (int i = 0; i < commands.length; i++) {
            System.out.print(commands[i]);
            for (int j = 0; j < (length - commands[i].length()); j++) {
                System.out.print(" ");
            }
            print("\t" + description[i]);
        }
    }

    public void input(String input) throws IOException {

        String[] commands = input.split("\\s+");

        switch (commands[0]) {
            case "create":
                if (commands.length == 1) controller.create(null);
                else controller.create(commands[1]);
                break;
            case "read":
                if(commands.length == 3)controller.read(Integer.parseInt(commands[1]), commands[2]);
                else if(commands.length == 2) controller.read(0, commands[1]);
                else System.out.println("Please enter the path");
                break;
            case "p":
                controller.print();
                break;
            case "s":
                controller.search(commands[1]);
                break;
            case "i":
                controller.insert(commands[1], commands[2]);
                break;
            case "r":
                controller.remove(commands[1]);
                break;
            default:
                System.out.println("wrong command! try again");
        }
    }

    private void print(String str) {
        System.out.println(str);
    }
}