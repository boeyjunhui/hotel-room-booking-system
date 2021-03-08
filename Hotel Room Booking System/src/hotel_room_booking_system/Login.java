package hotel_room_booking_system;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Login extends Frame implements ActionListener, WindowListener {

    // Initialize TextField, PasswordField, ComboBox & Button
    TextField txt_email;
    JPasswordField txt_password;
    JComboBox cbo_user_role;
    Button btn_login = new Button("Login");
    
    public Login() {
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("User Login");
        setLayout(new BorderLayout());
        addWindowListener(this);
        btn_login.addActionListener(this);

        // Panel 1
        Panel pnl_1 = new Panel();
        pnl_1.setBackground(Color.ORANGE);
        pnl_1.setLayout(new GridLayout(3, 1, 0, 0));
        
        Label lbl_empty = new Label("");
        pnl_1.add(lbl_empty);
        
        Label lbl_atlantis_hotel = new Label("Atlantis Hotel", Label.CENTER);
        lbl_atlantis_hotel.setFont(new Font("Palatino", Font.BOLD, 48));
        pnl_1.add(lbl_atlantis_hotel);
        
        Label lbl_hotel_room_booking_system = new Label("Hotel Room Booking System", Label.CENTER);
        lbl_hotel_room_booking_system.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        pnl_1.add(lbl_hotel_room_booking_system);
    
        // Panel 2
        Panel pnl_2 = new Panel();
        pnl_2.setBackground(Color.ORANGE);
        pnl_2.setLayout(new GridBagLayout());
        GridBagConstraints gbl_1 = new GridBagConstraints();
        
        gbl_1.insets = new Insets(20, 20, 0, 0);
        gbl_1.anchor = GridBagConstraints.LINE_END;
        
        gbl_1.gridx = 0;
        gbl_1.gridy = 1;
        Label lbl_email = new Label("Email:", Label.LEFT);
        lbl_email.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        pnl_2.add(lbl_email, gbl_1);
        
        gbl_1.gridx = 1;
        gbl_1.gridy = 1;
        txt_email = new TextField("", 22);
        txt_email.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        pnl_2.add(txt_email, gbl_1);
        
        gbl_1.gridx = 0;
        gbl_1.gridy = 2;
        Label lbl_password = new Label("Password:", Label.LEFT);
        lbl_password.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        pnl_2.add(lbl_password, gbl_1);
        
        gbl_1.gridx = 1;
        gbl_1.gridy = 2;
        txt_password = new JPasswordField("", 15);
        txt_password.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        pnl_2.add(txt_password, gbl_1);
        
        // Panel 3
        Panel pnl_3 = new Panel();
        pnl_3.setBackground(Color.ORANGE);
        pnl_3.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 50));
        
        String cbo_1[] = {"Select user role...", "Admin", "Staff"};
        cbo_user_role = new JComboBox(cbo_1);
        cbo_user_role.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        cbo_user_role.setPreferredSize(new Dimension(180, 40));
        pnl_3.add(cbo_user_role);
        
        btn_login.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        btn_login.setPreferredSize(new Dimension(140, 40));
        pnl_3.add(btn_login);
        
        add(pnl_1, "North");
        add(pnl_2, "Center");
        add(pnl_3, "South");
        
        setVisible(true);
    }
        
    public static void main(String[] args) {
        Login login = new Login();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == btn_login) {
            String email = txt_email.getText();
            String password = new String(txt_password.getPassword());
            String user_role = cbo_user_role.getSelectedItem().toString();

            if (txt_email.getText().equals("") || txt_password.getPassword().length == 0 || cbo_user_role.getSelectedItem().equals("Select user role...")) {
                JOptionPane.showMessageDialog(null, "Please fill in all details!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                try { 
                    FileReader file_reader = new FileReader("staff.txt");
                    BufferedReader buffered_reader = new BufferedReader(file_reader);

                    String line;
                    String[] line_array;
                    boolean data_exist = false;
                    
                    // Check whether input email and password is existing and matched in text file
                    while ((line = buffered_reader.readLine()) != null) {
                        line_array = line.split("/");
                        
                        if (line_array[4].equals(email) && line_array[5].equals(password) && line_array[7].equals(user_role) && cbo_user_role.getSelectedItem().equals("Admin")) {
                            data_exist = true;
                        } else if (line_array[4].equals(email) && line_array[5].equals(password) && line_array[7].equals(user_role) && cbo_user_role.getSelectedItem().equals("Staff")) {
                            data_exist = true;
                        }
                    }

                    if (data_exist == true && cbo_user_role.getSelectedItem().equals("Admin")) {
                        JOptionPane.showMessageDialog(null, "You have logged in successfully, admin!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);

                        ManageStaff manage_staff = new ManageStaff();
                        manage_staff.setVisible(true);
                        this.setVisible(false);
                    } else if (data_exist == true && cbo_user_role.getSelectedItem().equals("Staff")) {
                        JOptionPane.showMessageDialog(null, "You have logged in successfully, staff!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);

                        ManageRoomBooking manage_room_booking = new ManageRoomBooking();
                        manage_room_booking.setVisible(true);
                        this.setVisible(false);
                    } else if (data_exist == false) {
                        JOptionPane.showMessageDialog(null, "Failed to login! Email or password or user role \ndoes not match. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException f) {
                    JOptionPane.showMessageDialog(null, "Failed to login! Something went wrong, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
}
