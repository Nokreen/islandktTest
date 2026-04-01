import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IslandGUI extends JFrame {
    private Island island;
    private JTextArea statisticsArea;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private Timer simulationTimer;
    private boolean isRunning = false;

    public IslandGUI(Island island) {
        this.island = island;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Island Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        // Создаем основную панель
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Заголовок
        JLabel titleLabel = new JLabel("Island Ecosystem Simulation", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Панель управления
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Область статистики
        statisticsArea = new JTextArea();
        statisticsArea.setEditable(false);
        statisticsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(statisticsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Statistics"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Легенда
        JPanel legendPanel = createLegendPanel();
        mainPanel.add(legendPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Таймер для симуляции
        simulationTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                island.simulateDay();
                updateStatistics();
            }
        });

        updateStatistics();
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Control"));

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseSimulation();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSimulation();
            }
        });

        panel.add(startButton);
        panel.add(pauseButton);
        panel.add(resetButton);

        return panel;
    }

    private JPanel createLegendPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Legend"));

        JLabel predatorsLabel = new JLabel("Predators: 🐺 Wolf, 🐍 Boa, 🦊 Fox, 🦅 Eagle");
        JLabel herbivoresLabel = new JLabel("Herbivores: 🦆 Duck, 🐎 Horse, 🐃 Cow, 🐇 Rabbit, 🐑 Sheep, 🐁 Hamster");

        panel.add(predatorsLabel);
        panel.add(herbivoresLabel);

        return panel;
    }

    private void startSimulation() {
        if (!isRunning) {
            simulationTimer.start();
            isRunning = true;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
        }
    }

    private void pauseSimulation() {
        if (isRunning) {
            simulationTimer.stop();
            isRunning = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
        }
    }

    private void resetSimulation() {
        simulationTimer.stop();
        island = new Island();
        isRunning = false;
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        updateStatistics();
    }

    public void updateStatistics() {
        String stats = island.getStatistics();
        statisticsArea.setText(stats);
        statisticsArea.setCaretPosition(0);
    }
}