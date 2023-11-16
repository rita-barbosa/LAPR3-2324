package project.ui;

import project.ui.console.menu.MainMenuUI;

public class Main {
    public static void main(String[] args) {
        try {
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}