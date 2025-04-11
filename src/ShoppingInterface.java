import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ShoppingInterface extends JFrame {
    private JPanel panel1;
    private JButton mapButton;
    private JButton adviceButton; // Nowy przycisk do otwarcia porad

    public ShoppingInterface() {
        setTitle("Sklep ogrodniczy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel1.setBackground(new Color(240, 255, 240));

        JLabel label = new JLabel("Witaj w sklepie ogrodniczym!");
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        panel1.add(label);

        // Przycisk do otwierania okna porad
        adviceButton = new JButton("Porady Ogrodnicze");
        adviceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        adviceButton.setFont(new Font("Arial", Font.BOLD, 16));
        adviceButton.setBackground(new Color(76, 175, 80));
        adviceButton.setForeground(Color.WHITE);
        adviceButton.setFocusPainted(false);
        adviceButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        adviceButton.addActionListener(e -> PoradyOgrodniczePopup.popup(this)); // Otworzenie okna z poradami
        panel1.add(adviceButton);
        
        // Dodajemy trochę przestrzeni między przyciskami
        panel1.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Dodajemy więcej funkcji do sklepu ogrodniczego
        JButton productButton = new JButton("Przeglądaj Produkty");
        productButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        productButton.setFont(new Font("Arial", Font.BOLD, 16));
        productButton.setBackground(new Color(76, 175, 80));
        productButton.setForeground(Color.WHITE);
        productButton.setFocusPainted(false);
        productButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        productButton.addActionListener(e -> JOptionPane.showMessageDialog(this, 
                "Funkcja przeglądania produktów będzie dostępna wkrótce!", 
                "Informacja", JOptionPane.INFORMATION_MESSAGE));
        panel1.add(productButton);
        
        panel1.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton cartButton = new JButton("Koszyk");
        cartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartButton.setFont(new Font("Arial", Font.BOLD, 16));
        cartButton.setBackground(new Color(76, 175, 80));
        cartButton.setForeground(Color.WHITE);
        cartButton.setFocusPainted(false);
        cartButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        cartButton.addActionListener(e -> JOptionPane.showMessageDialog(this, 
                "Funkcja koszyka będzie dostępna wkrótce!", 
                "Informacja", JOptionPane.INFORMATION_MESSAGE));
        panel1.add(cartButton);
        
        panel1.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton logoutButton = new JButton("Wyloguj");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(244, 67, 54)); // Czerwony kolor dla wylogowania
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutButton.addActionListener(e -> {
            // Zamykamy obecne okno i otwieramy okno logowania
            dispose();
            SwingUtilities.invokeLater(() -> {
                LoginInterface loginInterface = new LoginInterface();
                loginInterface.setVisible(true);
            });
        });
        panel1.add(logoutButton);
        
        // WAŻNE: Dodajemy panel do ramki
        add(panel1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ShoppingInterface().setVisible(true);
        });
    }
}
