import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PoradyOgrodniczePopup extends JDialog {
    private JFrame parentFrame;
    private JPanel advicePanel;
    private JTextField searchField;
    private List<GardenAdvice> porady;

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

        // Wczytanie porad z bazy danych
        porady = loadAdviceFromDatabase();
        displayAdvice(porady);

        JScrollPane scrollPane = new JScrollPane(advicePanel);
        scrollPane.setOpaque(false); // Ustawienie przezroczystości dla panelu z przewijaniem
        scrollPane.getViewport().setOpaque(false); // Ustawienie przezroczystości dla widoku wewnątrz przewijania

        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }

    // Klasa reprezentująca pojedynczą poradę ogrodniczą
    private static class GardenAdvice {
        private int id;
        private String title;
        private String content;
        private boolean userAdded;
        
        public GardenAdvice(int id, String title, String content, boolean userAdded) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.userAdded = userAdded;
        }
        
        public int getId() {
            return id;
        }
        
        public String getTitle() {
            return title;
        }
        
        public String getContent() {
            return content;
        }
        
        public boolean isUserAdded() {
            return userAdded;
        }
    }

    private void displayAdvice(List<GardenAdvice> porady) {
        advicePanel.removeAll();
        for (GardenAdvice porada : porady) {
            addAdviceToPanel(advicePanel, porada);
        }
        advicePanel.revalidate();
        advicePanel.repaint();
    }

    private void searchAdvice() {
        String query = searchField.getText().toLowerCase();
        List<GardenAdvice> filteredAdvice = porady.stream()
                .filter(porada -> porada.getTitle().toLowerCase().contains(query) || 
                                 porada.getContent().toLowerCase().contains(query))
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
                if (insertAdviceToDatabase(title, content, true)) {
                    // Przeładuj porady z bazy danych, aby uzyskać nowy identyfikator
                    porady = loadAdviceFromDatabase();
                    displayAdvice(porady);
                    JOptionPane.showMessageDialog(this, "Porada została dodana pomyślnie!", 
                            "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Błąd podczas dodawania porady.", 
                            "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showRandomAdvice() {
        if (!porady.isEmpty()) {
            int randomIndex = (int) (Math.random() * porady.size());
            GardenAdvice randomAdvice = porady.get(randomIndex);
            JOptionPane.showMessageDialog(this, randomAdvice.getContent(), randomAdvice.getTitle(), JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nie znaleziono porad ogrodniczych.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addAdviceToPanel(JPanel panel, GardenAdvice advice) {
        JPanel advicePanel = new JPanel(new BorderLayout());
        advicePanel.setOpaque(false); // Ustawienie przezroczystości dla panelu z poradą

        JLabel titleLabel = new JLabel(advice.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(51, 153, 51)); // ciemny zielony
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        advicePanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(advice.getContent());
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setForeground(Color.BLACK);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        advicePanel.add(contentArea, BorderLayout.CENTER);

        if (advice.isUserAdded()) {
            JButton deleteButton = new JButton("Usuń");
            deleteButton.setToolTipText("Kliknij, aby usunąć tę poradę");
            deleteButton.addActionListener(e -> {
                if (deleteAdviceFromDatabase(advice.getId())) {
                    porady.remove(advice);
                    displayAdvice(porady);
                    JOptionPane.showMessageDialog(this, "Porada została usunięta pomyślnie!", 
                            "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Błąd podczas usuwania porady.", 
                            "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            });
            advicePanel.add(deleteButton, BorderLayout.EAST);
        }

        advicePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 153, 51)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        panel.add(advicePanel);
    }

    private List<GardenAdvice> loadAdviceFromDatabase() {
        List<GardenAdvice> adviceList = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, title, content, user_added FROM garden_advice ORDER BY id")) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                boolean userAdded = rs.getBoolean("user_added");
                
                adviceList.add(new GardenAdvice(id, title, content, userAdded));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Błąd podczas wczytywania porad: " + e.getMessage(), 
                    "Błąd bazy danych", JOptionPane.ERROR_MESSAGE);
        }
        
        return adviceList;
    }
    
    private boolean insertAdviceToDatabase(String title, String content, boolean userAdded) {
        String sql = "INSERT INTO garden_advice (title, content, user_added) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setBoolean(3, userAdded);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Błąd podczas dodawania porady: " + e.getMessage(), 
                    "Błąd bazy danych", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private boolean deleteAdviceFromDatabase(int id) {
        String sql = "DELETE FROM garden_advice WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Błąd podczas usuwania porady: " + e.getMessage(), 
                    "Błąd bazy danych", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Metoda do dodania przykładowych porad ogrodniczych do bazy danych
    public static void initializeDefaultAdvice() {
        String[][] defaultAdvice = {
            {"Nawadnianie roślin", "Podlewaj rośliny wcześnie rano lub wieczorem, aby zmniejszyć parowanie. Zwracaj uwagę na indywidualne potrzeby każdej rośliny."},
            {"Naturalne nawozy", "Kompost jest doskonałym, naturalnym nawozem dla roślin ogrodowych. Przygotuj swój własny kompost z odpadów kuchennych i ogrodowych."},
            {"Ochrona przed szkodnikami", "Stosuj naturalne metody ochrony przed szkodnikami, takie jak nasadzenia roślin odstraszających szkodniki lub sprowadzanie naturalnych drapieżników."},
            {"Sadzenie roślin", "Wybieraj rośliny odpowiednie dla twojego klimatu i warunków glebowych. Sprawdź pH gleby przed sadzeniem, aby zapewnić optymalne warunki wzrostu."}
        };
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO garden_advice (title, content, user_added) VALUES (?, ?, false) ON CONFLICT DO NOTHING")) {
            
            for (String[] advice : defaultAdvice) {
                pstmt.setString(1, advice[0]);
                pstmt.setString(2, advice[1]);
                pstmt.executeUpdate();
            }
            
        } catch (SQLException e) {
            System.err.println("Błąd podczas inicjalizacji domyślnych porad: " + e.getMessage());
        }
    }

    public static void popup(JFrame parent) {
        // Dodajemy przykładowe porady przy każdym uruchomieniu aplikacji (zostaną dodane tylko, jeśli nie istnieją)
        initializeDefaultAdvice();
        
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

