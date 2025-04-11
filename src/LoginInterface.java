import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginInterface extends JFrame {
    private JTextField poleUzytkownika;
    private JPasswordField poleHasla;
    private JCheckBox pokazHaslo;
    private JButton przyciskLogowania;
    private JButton przyciskRejestracji;
    private JButton przyciskZapomnialesHasla;

    public LoginInterface() {
        setSize(900, 600);
        setTitle("Logowanie");

        JPanel panelGlowny = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(70, 130, 180);
                Color color2 = new Color(135, 206, 235);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panelGlowny.setLayout(new BoxLayout(panelGlowny, BoxLayout.Y_AXIS));
        panelGlowny.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelTytulowy = new JPanel();
        panelTytulowy.setOpaque(false);
        JLabel etykietaTytulowa = new JLabel(" Dołącz już teraz i odkryj świat zielonych inspiracji!");
        etykietaTytulowa.setForeground(Color.WHITE);
        etykietaTytulowa.setFont(new Font("Arial", Font.BOLD, 32));
        panelTytulowy.add(etykietaTytulowa);

        JPanel panelFormularza = new JPanel();
        panelFormularza.setLayout(new BoxLayout(panelFormularza, BoxLayout.Y_AXIS));
        panelFormularza.setOpaque(false);
        panelFormularza.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel etykietaPodpowiedz = new JLabel("Wprowadź dane logowania");
        etykietaPodpowiedz.setForeground(Color.WHITE);
        etykietaPodpowiedz.setFont(new Font("Arial", Font.PLAIN, 20));
        etykietaPodpowiedz.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormularza.add(etykietaPodpowiedz);

        panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel etykietaUzytkownika = new JLabel("Nazwa użytkownika:");
        etykietaUzytkownika.setForeground(Color.WHITE);
        etykietaUzytkownika.setFont(new Font("Arial", Font.PLAIN, 18));
        etykietaUzytkownika.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormularza.add(etykietaUzytkownika);

        poleUzytkownika = new JTextField(10);
        poleUzytkownika.setMaximumSize(new Dimension(300, 40));
        poleUzytkownika.setFont(new Font("Arial", Font.PLAIN, 18));
        poleUzytkownika.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelFormularza.add(poleUzytkownika);
        poleUzytkownika.setToolTipText("Wprowadz Nazwe Uzytkownika");

        panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel etykietaHasla = new JLabel("Hasło:");
        etykietaHasla.setForeground(Color.WHITE);
        etykietaHasla.setFont(new Font("Arial", Font.PLAIN, 18));
        etykietaHasla.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormularza.add(etykietaHasla);

        poleHasla = new JPasswordField(10);
        poleHasla.setMaximumSize(new Dimension(300, 40));
        poleHasla.setFont(new Font("Arial", Font.PLAIN, 18));
        poleHasla.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelFormularza.add(poleHasla);
        poleHasla.setToolTipText("Podaj Hasło");

        pokazHaslo = new JCheckBox("Pokaż hasło");
        pokazHaslo.setForeground(Color.WHITE);
        pokazHaslo.setOpaque(false);
        pokazHaslo.setFont(new Font("Arial", Font.PLAIN, 18));
        pokazHaslo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pokazHaslo.isSelected()) {
                    poleHasla.setEchoChar((char) 0);
                } else {
                    poleHasla.setEchoChar('•');
                }
            }
        });
        panelFormularza.add(pokazHaslo);

        panelFormularza.add(Box.createRigidArea(new Dimension(0, 40)));

        przyciskLogowania = new JButton("Zaloguj się");
        przyciskLogowania.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskLogowania.setBackground(new Color(34, 139, 34));
        przyciskLogowania.setForeground(Color.WHITE);
        przyciskLogowania.setFocusPainted(false);
        przyciskLogowania.setFont(new Font("Arial", Font.BOLD, 18));
        przyciskLogowania.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        przyciskLogowania.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = poleUzytkownika.getText().trim();
                String password = new String(poleHasla.getPassword()).trim();

                if (sprawdzDaneLogowania(username, password)) {
                    pokazPowiadomienie("Zalogowano pomyślnie!");
                    // Otwarcie nowego okna sklepu
                    new ShoppingInterface().setVisible(true);
                    // Zamknięcie okna logowania
                    dispose();
                } else {
                    pokazPowiadomienie("Błędna nazwa użytkownika lub hasło.");
                }
            }
        });

        przyciskRejestracji = new JButton("Zarejestruj się");
        przyciskRejestracji.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskRejestracji.setBackground(new Color(255, 165, 0));
        przyciskRejestracji.setForeground(Color.WHITE);
        przyciskRejestracji.setFocusPainted(false);
        przyciskRejestracji.setFont(new Font("Arial", Font.BOLD, 18));
        przyciskRejestracji.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        przyciskRejestracji.setCursor(new Cursor(Cursor.HAND_CURSOR));
        przyciskRejestracji.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationInterface registration = new RegistrationInterface();
                registration.setVisible(true);
            }
        });

        przyciskZapomnialesHasla = new JButton("Zapomniałeś hasła?");
        przyciskZapomnialesHasla.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskZapomnialesHasla.setBackground(new Color(255, 69, 0));
        przyciskZapomnialesHasla.setForeground(Color.WHITE);
        przyciskZapomnialesHasla.setFocusPainted(false);
        przyciskZapomnialesHasla.setFont(new Font("Arial", Font.BOLD, 18));
        przyciskZapomnialesHasla.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        przyciskZapomnialesHasla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        przyciskZapomnialesHasla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zmieniamy zawartość okna na formularz do wprowadzenia numeru telefonu
                panelFormularza.removeAll();

                // Dodajemy etykietę i pole do wpisania numeru telefonu
                JLabel etykietaNumerTelefonu = new JLabel("Wprowadź numer telefonu:");
                etykietaNumerTelefonu.setForeground(Color.WHITE);
                etykietaNumerTelefonu.setFont(new Font("Arial", Font.PLAIN, 18));
                etykietaNumerTelefonu.setAlignmentX(Component.CENTER_ALIGNMENT);
                panelFormularza.add(etykietaNumerTelefonu);

                JTextField poleNumeruTelefonu = new JTextField(10);
                poleNumeruTelefonu.setMaximumSize(new Dimension(300, 40));
                poleNumeruTelefonu.setFont(new Font("Arial", Font.PLAIN, 18));
                poleNumeruTelefonu.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                panelFormularza.add(poleNumeruTelefonu);

                panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));

                // Dodajemy przycisk do wysłania tokenu
                JButton wyslijTokenButton = new JButton("Wyślij token");
                wyslijTokenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                wyslijTokenButton.setBackground(new Color(34, 139, 34));
                wyslijTokenButton.setForeground(Color.WHITE);
                wyslijTokenButton.setFocusPainted(false);
                wyslijTokenButton.setFont(new Font("Arial", Font.BOLD, 18));
                wyslijTokenButton.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
                wyslijTokenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String telefon = poleNumeruTelefonu.getText().trim();
                        // Kod do wysłania tokenu
                    }
                });

                panelFormularza.add(wyslijTokenButton);

                // Odświeżamy okno, aby wyświetlić nowy formularz
                panelFormularza.revalidate();
                panelFormularza.repaint();
            }
        });

        panelFormularza.add(przyciskLogowania);
        panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));
        panelFormularza.add(przyciskRejestracji);
        panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));
        panelFormularza.add(przyciskZapomnialesHasla);

        panelGlowny.add(panelTytulowy);
        panelGlowny.add(Box.createRigidArea(new Dimension(0, 40)));
        panelGlowny.add(panelFormularza);

        add(panelGlowny);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private boolean sprawdzDaneLogowania(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password); // In a production environment, you should hash and verify passwords
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if user exists with given credentials
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Błąd podczas logowania: " + e.getMessage(), 
                    "Błąd logowania", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void pokazPowiadomienie(String message) {
        JDialog dialog = new JDialog(this, "Powiadomienie", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(message);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton okButton = new JButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(okButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginInterface login = new LoginInterface();
                login.setVisible(true);
            }
        });
    }
}
