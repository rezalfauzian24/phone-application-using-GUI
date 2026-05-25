import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AplikasiKontak extends JFrame {

    private JTextField txtNama, txtNomor;
    private JTable tabelKontak;
    private DefaultTableModel tableModel;
    private JButton btnTambah, btnEdit, btnHapus, btnClear;
    private int rowTerpilih = -1;

    public AplikasiKontak() {
        setTitle("Aplikasi Kontak Telepon");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelInput.add(new JLabel("Nama:"), gbc);

        txtNama = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        panelInput.add(txtNama, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelInput.add(new JLabel("No. Telepon:"), gbc);

        txtNomor = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        panelInput.add(txtNomor, gbc);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");

        btnTambah.setBackground(new Color(46, 204, 113));
        btnTambah.setForeground(Color.WHITE);
        btnEdit.setBackground(new Color(52, 152, 219));
        btnEdit.setForeground(Color.WHITE);
        btnHapus.setBackground(new Color(231, 76, 60));
        btnHapus.setForeground(Color.WHITE);

        panelTombol.add(btnTambah);
        panelTombol.add(btnEdit);
        panelTombol.add(btnHapus);
        panelTombol.add(btnClear);

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelInput, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);
        add(panelAtas, BorderLayout.NORTH);

        String[] kolom = {"Nama", "Nomor Telepon"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelKontak = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelKontak);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar"));
        add(scrollPane, BorderLayout.CENTER);

        tabelKontak.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rowTerpilih = tabelKontak.getSelectedRow();
                if (rowTerpilih != -1) {
                    txtNama.setText(tableModel.getValueAt(rowTerpilih, 0).toString());
                    txtNomor.setText(tableModel.getValueAt(rowTerpilih, 1).toString());
                }
            }
        });

        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = txtNama.getText().trim();
                String nomor = txtNomor.getText().trim();
                if (nama.isEmpty() || nomor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    tableModel.addRow(new Object[]{nama, nomor});
                    clearInput();
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rowTerpilih == -1) {
                    JOptionPane.showMessageDialog(null, "Pilih data!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String namaBaru = txtNama.getText().trim();
                String nomorBaru = txtNomor.getText().trim();
                if (namaBaru.isEmpty() || nomorBaru.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    tableModel.setValueAt(namaBaru, rowTerpilih, 0);
                    tableModel.setValueAt(nomorBaru, rowTerpilih, 1);
                    clearInput();
                    JOptionPane.showMessageDialog(null, "Diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rowTerpilih == -1) {
                    JOptionPane.showMessageDialog(null, "Pilih data!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int konfirmasi = JOptionPane.showConfirmDialog(null, "Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(rowTerpilih);
                    clearInput();
                    JOptionPane.showMessageDialog(null, "Dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInput();
            }
        });
    }

    private void clearInput() {
        txtNama.setText("");
        txtNomor.setText("");
        tabelKontak.clearSelection();
        rowTerpilih = -1;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AplikasiKontak().setVisible(true);
            }
        });
    }
}