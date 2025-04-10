import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
 public class RegistrationInterface extends JFrame {
     private JTextField poleImie;
     private JTextField poleNazwisko;
     private JTextField poleEmail;
     private JTextField poleUzytkownika;
     private JPasswordField poleHasla;
     private JButton przyciskRejestracji;
     private JLabel messageLabel;

     private static final String USERS_FILE = "C:\\Users\\Admin\\Desktop\\uzytkownicy.txt"; // Ujednolicona ścieżka do pliku użytkowników // Ścieżka do pliku użytkowników

     public RegistrationInterface() {
         setTitle("Rejestracja");
         setSize(400, 500);
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         setResizable(false); // Blokowanie zmiany rozmiaru okna

         JPanel panelGlowny = new JPanel();
         panelGlowny.setLayout(new BoxLayout(panelGlowny, BoxLayout.Y_AXIS));
         panelGlowny.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
         panelGlowny.setBackground(new Color(240, 255, 240)); // Jasnozielone tło

         JPanel panelTytulowy = new JPanel();
         panelTytulowy.setBackground(new Color(0, 128, 0)); // Zielony
         JLabel etykietaTytulowa = new JLabel("Formularz Rejestracji");
         etykietaTytulowa.setFont(new Font("Arial", Font.BOLD, 28));
         etykietaTytulowa.setForeground(Color.WHITE);
         panelTytulowy.add(etykietaTytulowa);

         JPanel panelFormularza = new JPanel();
         panelFormularza.setLayout(new BoxLayout(panelFormularza, BoxLayout.Y_AXIS));
         panelFormularza.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
         panelFormularza.setBackground(new Color(240, 255, 240)); // Jasnozielone tło

         JLabel etykietaPodpowiedz = new JLabel("Wprowadź dane rejestracyjne");
         etykietaPodpowiedz.setFont(new Font("Arial", Font.PLAIN, 16));
         etykietaPodpowiedz.setAlignmentX(Component.CENTER_ALIGNMENT);
         panelFormularza.add(etykietaPodpowiedz);

         panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));

         JLabel etykietaImie = new JLabel("Imię:");
         etykietaImie.setFont(new Font("Arial", Font.PLAIN, 14));
         etykietaImie.setAlignmentX(Component.CENTER_ALIGNMENT);
         panelFormularza.add(etykietaImie);

         poleImie = new JTextField(10);
         poleImie.setMaximumSize(new Dimension(200, 30));
         poleImie.setFont(new Font("Arial", Font.PLAIN, 14));
         poleImie.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         panelFormularza.add(poleImie);
         poleImie.setToolTipText("Wprowadź swoje imię");

         panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));

         JLabel etykietaNazwisko = new JLabel("Nazwisko:");
         etykietaNazwisko.setFont(new Font("Arial", Font.PLAIN, 14));
         etykietaNazwisko.setAlignmentX(Component.CENTER_ALIGNMENT);
         panelFormularza.add(etykietaNazwisko);

         poleNazwisko = new JTextField(10);
         poleNazwisko.setMaximumSize(new Dimension(200, 30));
         poleNazwisko.setFont(new Font("Arial", Font.PLAIN, 14));
         poleNazwisko.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         panelFormularza.add(poleNazwisko);
         poleNazwisko.setToolTipText("Wprowadz swoje Nazwisko");

         panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));

         JLabel etykietaEmail = new JLabel("Email:");
         etykietaEmail.setFont(new Font("Arial", Font.PLAIN, 14));
         etykietaEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
         panelFormularza.add(etykietaEmail);

         poleEmail = new JTextField(10);
         poleEmail.setMaximumSize(new Dimension(200, 30));
         poleEmail.setFont(new Font("Arial", Font.PLAIN, 14));
         poleEmail.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         panelFormularza.add(poleEmail);
         poleEmail.setToolTipText("Wprowadz swoj Email");

         panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));

         JLabel etykietaUzytkownika = new JLabel("Nazwa użytkownika:");
         etykietaUzytkownika.setFont(new Font("Arial", Font.PLAIN, 14));
         etykietaUzytkownika.setAlignmentX(Component.CENTER_ALIGNMENT);
         panelFormularza.add(etykietaUzytkownika);

         poleUzytkownika = new JTextField(10);
         poleUzytkownika.setMaximumSize(new Dimension(200, 30));
         poleUzytkownika.setFont(new Font("Arial", Font.PLAIN, 14));
         poleUzytkownika.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         panelFormularza.add(poleUzytkownika);
         poleUzytkownika.setToolTipText("Wprowadz Nazwe Uytkownika");

         panelFormularza.add(Box.createRigidArea(new Dimension(0, 20)));

         JLabel etykietaHasla = new JLabel("Hasło:");
         etykietaHasla.setFont(new Font("Arial", Font.PLAIN, 14));
         etykietaHasla.setAlignmentX(Component.CENTER_ALIGNMENT);
         panelFormularza.add(etykietaHasla);

         poleHasla = new JPasswordField(10);
         poleHasla.setMaximumSize(new Dimension(200, 30));
         poleHasla.setFont(new Font("Arial", Font.PLAIN, 14));
         poleHasla.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         panelFormularza.add(poleHasla);
         poleHasla.setToolTipText("Wprowadź Hasło");

         panelFormularza.add(Box.createRigidArea(new Dimension(0, 30)));

         przyciskRejestracji = new JButton("Zarejestruj się");
         przyciskRejestracji.setAlignmentX(Component.CENTER_ALIGNMENT);
         przyciskRejestracji.setBackground(new Color(0, 128, 0)); // Zielony
         przyciskRejestracji.setForeground(Color.WHITE);
         przyciskRejestracji.setFocusPainted(false);
         przyciskRejestracji.setFont(new Font("Arial", Font.BOLD, 16));
         przyciskRejestracji.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
         przyciskRejestracji.setCursor(new Cursor(Cursor.HAND_CURSOR));
         przyciskRejestracji.setToolTipText("Kliknij, aby się zarejestrować");
         przyciskRejestracji.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 try {
                     validateForm();
                     zapiszUzytkownika();
                     messageLabel.setText("Rejestracja zakończona pomyślnie!");
                     messageLabel.setForeground(Color.GREEN);
                 } catch (RegistrationException ex) {
                     messageLabel.setText(ex.getMessage());
                     messageLabel.setForeground(Color.RED);
                 }
             }
         });

         // Efekt najechania
         przyciskRejestracji.addMouseListener(new MouseAdapter() {
             public void mouseEntered(MouseEvent evt) {
                 przyciskRejestracji.setBackground(new Color(34, 139, 34)); // Zielony, ciemniejszy
             }

             public void mouseExited(MouseEvent evt) {
                 przyciskRejestracji.setBackground(new Color(0, 128, 0)); // Powrót do zielonego
             }
         });

         panelFormularza.add(przyciskRejestracji);

         messageLabel = new JLabel("", SwingConstants.CENTER);
         messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         panelFormularza.add(messageLabel);

         panelGlowny.add(panelTytulowy);
         panelGlowny.add(Box.createRigidArea(new Dimension(0, 30)));
         panelGlowny.add(panelFormularza);
         getContentPane().add(panelGlowny);
         pack();
         setLocationRelativeTo(null); // Wyśrodkowanie okna
         setVisible(true);
     }

     private void validateForm() throws RegistrationException {
         String imie = poleImie.getText();
         String nazwisko = poleNazwisko.getText();
         String email = poleEmail.getText();
         String username = poleUzytkownika.getText();
         String password = new String(poleHasla.getPassword());

         if (imie.isEmpty() || nazwisko.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
             throw new RegistrationException("Wszystkie pola są wymagane.");
         }

         if (!Pattern.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", email)) {
             throw new RegistrationException("Podaj poprawny adres e-mail.");
         }


         if (!Pattern.matches("^[a-zA-Z0-9_]+$", username)) {
             throw new RegistrationException("Nazwa użytkownika może zawierać tylko litery, cyfry i _.");
         }

         if (password.length() < 8) {
             throw new RegistrationException("Hasło musi mieć co najmniej 8 znaków.");
         }
     }
     private void zapiszUzytkownika() {
         String imie = poleImie.getText().trim();
         String nazwisko = poleNazwisko.getText().trim();
         String email = poleEmail.getText().trim();
         String username = poleUzytkownika.getText().trim();
         String password = new String(poleHasla.getPassword()).trim();

         try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
             writer.write(imie + "," + nazwisko + "," + email + "," + username + "," + password);
             writer.newLine();
             writer.flush(); // Ensure data is written to file immediately
         } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Błąd zapisu użytkownika do pliku.", "Błąd", JOptionPane.ERROR_MESSAGE);
         }
     }

     private static class RegistrationException extends Exception {
         public RegistrationException(String message) {
             super(message);
         }
     }

     public static void main(String[] args) {
         SwingUtilities.invokeLater(RegistrationInterface::new);
     }
 }