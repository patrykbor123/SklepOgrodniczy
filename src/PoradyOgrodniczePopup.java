import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PoradyOgrodniczePopup extends JDialog {
    private JFrame parentFrame;
    private JPanel advicePanel;
    private JTextField searchField;
    private List<String[]> porady;
    private String filePath = "C:\\Users\\Admin\\Desktop\\porady1.txt";
    private List<String[]> userAddedPorady = new ArrayList<>(); // Lista porad dodanych przez użytkownika

    public PoradyOgrodniczePopup(JFrame parent) {
        super(parent, "Porady ogrodnicze", true); // true oznacza, że okno jest modalne
        this.parentFrame = parent;
        setSize(600, 600);  // lekko zwiększony rozmiar okna
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(235, 255, 235)); // Jasny zielony, kolor ogrodniczy

        // Górny panel z opcjami
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setToolTipText("Wpisz frazę, aby wyszukać poradę");
        JButton searchButton = new JButton("Szukaj");
        searchButton.setToolTipText("Kliknij, aby wyszukać porady");
        searchButton.addActionListener(e -> searchAdvice());
        JButton addButton = new JButton("Dodaj Poradę");
        addButton.setToolTipText("Kliknij, aby dodać nową poradę");
        addButton.addActionListener(e -> addAdvice());
        JButton randomButton = new JButton("Losowa Porada");
        randomButton.setToolTipText("Kliknij, aby wyświetlić losową poradę");
        randomButton.addActionListener(e -> showRandomAdvice());
        JButton backButton = new JButton("Cofnij");
        backButton.setToolTipText("Kliknij, aby wrócić do menu");
        backButton.addActionListener(e -> dispose()); // Zamknięcie okna dialogowego

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(searchButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(randomButton);
        buttonsPanel.add(backButton);

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);

        advicePanel = new JPanel();
        advicePanel.setLayout(new BoxLayout(advicePanel, BoxLayout.Y_AXIS));
        advicePanel.setOpaque(false); // Ustawienie przezroczystości dla panelu z poradami
        advicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 51)), "Porady ogrodnicze",
                0, 0, new Font("Arial", Font.BOLD, 18), Color.BLACK));

        // Wczytanie porad z pliku
        porady = wczytajPoradyZPliku(filePath);
        displayAdvice(porady);

        JScrollPane scrollPane = new JScrollPane(advicePanel);
        scrollPane.setOpaque(false); // Ustawienie przezroczystości dla panelu z przewijaniem
        scrollPane.getViewport().setOpaque(false); // Ustawienie przezroczystości dla widoku wewnątrz przewijania

        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }

    private void displayAdvice(List<String[]> porady) {
        advicePanel.removeAll();
        for (String[] porada : porady) {
            addAdviceToPanel(advicePanel, porada[0], porada[1], userAddedPorady.contains(porada));
        }
        advicePanel.revalidate();
        advicePanel.repaint();
    }

    private void searchAdvice() {
        String query = searchField.getText().toLowerCase();
        List<String[]> filteredAdvice = porady.stream()
                .filter(porada -> porada[0].toLowerCase().contains(query) || porada[1].toLowerCase().contains(query))
                .collect(Collectors.toList());
        displayAdvice(filteredAdvice);
    }

    private void addAdvice() {
        JTextField titleField = new JTextField();
        JTextArea contentArea = new JTextArea(5, 20);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JLabel("Tytuł:"), BorderLayout.NORTH);
        titlePanel.add(titleField, BorderLayout.CENTER);
        inputPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("Treść:"), BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        inputPanel.add(contentPanel, BorderLayout.CENTER);

        titleField.setToolTipText("Wpisz tytuł porady");
        contentArea.setToolTipText("Wpisz treść porady");

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Dodaj Poradę", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String content = contentArea.getText();
            if (!title.isEmpty() && !content.isEmpty()) {
                try (FileWriter fw = new FileWriter(filePath, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter out = new PrintWriter(bw)) {
                    out.println(title + "," + content);
                    String[] newPorada = new String[]{title, content};
                    porady.add(newPorada);
                    userAddedPorady.add(newPorada);
                    displayAdvice(porady);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showRandomAdvice() {
        if (!porady.isEmpty()) {
            int randomIndex = (int) (Math.random() * porady.size());
            String[] randomAdvice = porady.get(randomIndex);
            JOptionPane.showMessageDialog(this, randomAdvice[1], randomAdvice[0], JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addAdviceToPanel(JPanel panel, String title, String content, boolean userAdded) {
        JPanel advice = new JPanel(new BorderLayout());
        advice.setOpaque(false); // Ustawienie przezroczystości dla panelu z poradą

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(51, 153, 51)); // ciemny zielony
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        advice.add(titleLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setForeground(Color.BLACK);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        advice.add(contentArea, BorderLayout.CENTER);

        if (userAdded) {
            JButton deleteButton = new JButton("Usuń");
            deleteButton.setToolTipText("Kliknij, aby usunąć tę poradę");
            deleteButton.addActionListener(e -> {
                porady.removeIf(p -> p[0].equals(title) && p[1].equals(content));
                userAddedPorady.removeIf(p -> p[0].equals(title) && p[1].equals(content));
                saveAdviceToFile();
                displayAdvice(porady);
            });
            advice.add(deleteButton, BorderLayout.EAST);
        }

        advice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 153, 51)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        panel.add(advice);
    }

    private void saveAdviceToFile() {
        try (FileWriter fw = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (String[] porada : porady) {
                out.println(porada[0] + "," + porada[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String[]> wczytajPoradyZPliku(String nazwaPliku) {
        List<String[]> porady = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nazwaPliku))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    porady.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return porady;
    }

    public static void popup(JFrame parent) {
        PoradyOgrodniczePopup popup = new PoradyOgrodniczePopup(parent);
        popup.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton openButton = new JButton("Otwórz okno z poradami");
        openButton.addActionListener(e -> PoradyOgrodniczePopup.popup(frame));

        frame.setLayout(new FlowLayout());
        frame.add(openButton);
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

