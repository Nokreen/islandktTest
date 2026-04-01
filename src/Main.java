import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Создаем остров и GUI в потоке обработки событий Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Island island = new Island();
                IslandGUI gui = new IslandGUI(island);
                gui.setVisible(true);
            }
        });
    }
}