package Task01;


public class Tui {

    public void printMenu() {

        String[] commands = {"create Implementierung", "read [n] Dateiname", "p", "s deutsch", "i deutsch englisch", "r deutsch", "exit"};
        int length = commands[0].length();
        for (String s : commands) {
            if (s.length() > length) {
                length = s.length();
            }
        }
        String[] description = {"Legt ein Dictionary an. SortedArrayDictionary ist voreingestellt. ",
                "Liest die ersten n Einträge der Datei in das Dictionary ein. Wird n weggelassen, dann werden alle Einträge eingelesen.",
                "Gibt alle Einträge des Dictionary in der Konsole aus (print).",
                "Gibt das entsprechende englische Wort aus (search). ",
                "Fügt ein neues Wortpaar in das Dictionary ein (insert).",
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

    public void input(String input) {
        System.out.println(input);
        int a = input.indexOf(" ");
        String commands = input.substring(0, a);

        String temp = input.substring(a + 1);
        int b = temp.indexOf(" ");



        switch (commands) {
            case "create":
            case "read":
            case "p":
            case "s":
            case "i":
            case "r":
                break;
        }
    }


    private void print(String str) {
        System.out.println(str);
    }
}
