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
        adviceButton.addActionListener(e -> PoradyOgrodniczePopup.popup(this)); // Otworzenie okna z poradami
        panel1.add(adviceButton);


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
