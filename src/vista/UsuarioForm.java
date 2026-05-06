package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Formulario principal - Interfaz gráfica del CRUD de Usuarios
 * Conectado a Oracle SQL Plus 10g mediante JDBC
 */
public class UsuarioForm extends JFrame {

    // ── Componentes del formulario ─────────────────────────────────────────
    private JTextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private UsuarioDAO dao;

    // ══════════════════════════════════════════════════════════════
    //  Constructor
    // ══════════════════════════════════════════════════════════════
    public UsuarioForm() {
        dao = new UsuarioDAO();
        initComponents();
        cargarTabla();
    }

    // ══════════════════════════════════════════════════════════════
    //  Inicializar componentes
    // ══════════════════════════════════════════════════════════════
    private void initComponents() {
        setTitle("Parcial 2 - UDI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 560);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(240, 244, 248));

        // ── Panel superior: título ─────────────────────────────────────────
        JLabel titulo = new JLabel("Gestión de Usuarios", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(30, 80, 150));
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // ── Panel formulario (izquierda) ───────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 230), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panelForm.setPreferredSize(new Dimension(260, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;

        // ID (solo lectura)
        panelForm.add(crearLabel("ID (auto):"), gbc);
        gbc.gridy++;
        txtId = crearCampo(true);
        panelForm.add(txtId, gbc);

        gbc.gridy++;
        panelForm.add(crearLabel("Nombre *"), gbc);
        gbc.gridy++;
        txtNombre = crearCampo(false);
        panelForm.add(txtNombre, gbc);

        gbc.gridy++;
        panelForm.add(crearLabel("Apellido *"), gbc);
        gbc.gridy++;
        txtApellido = crearCampo(false);
        panelForm.add(txtApellido, gbc);

        gbc.gridy++;
        panelForm.add(crearLabel("Email *"), gbc);
        gbc.gridy++;
        txtEmail = crearCampo(false);
        panelForm.add(txtEmail, gbc);

        gbc.gridy++;
        panelForm.add(crearLabel("Teléfono"), gbc);
        gbc.gridy++;
        txtTelefono = crearCampo(false);
        panelForm.add(txtTelefono, gbc);

        // ── Botones ────────────────────────────────────────────────────────
        gbc.gridy++;
        gbc.insets = new Insets(15, 5, 3, 5);
        btnGuardar    = crearBoton("💾 Guardar",     new Color(46, 125, 50));
        panelForm.add(btnGuardar, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(3, 5, 3, 5);
        btnActualizar = crearBoton("✏️ Actualizar",  new Color(21, 101, 192));
        panelForm.add(btnActualizar, gbc);

        gbc.gridy++;
        btnEliminar   = crearBoton("🗑️ Eliminar",    new Color(183, 28, 28));
        panelForm.add(btnEliminar, gbc);

        gbc.gridy++;
        btnLimpiar    = crearBoton("🔄 Limpiar",     new Color(100, 100, 100));
        panelForm.add(btnLimpiar, gbc);

        // ── Tabla (derecha) ────────────────────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(26);
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(new Color(30, 30, 30));
        tabla.setSelectionBackground(new Color(21, 101, 192));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setGridColor(new Color(200, 215, 235));
        tabla.setShowGrid(true);
        tabla.getTableHeader().setOpaque(true);
        tabla.getTableHeader().setBackground(new Color(30, 80, 150));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(90);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)));

        // ── Ensamblar layout ───────────────────────────────────────────────
        panelPrincipal.add(panelForm, BorderLayout.WEST);
        panelPrincipal.add(scroll,    BorderLayout.CENTER);
        add(panelPrincipal);

        // ── Eventos ────────────────────────────────────────────────────────
        btnGuardar.addActionListener(e -> guardarUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Seleccionar fila en tabla → llenar formulario
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtEmail.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtTelefono.setText(modeloTabla.getValueAt(fila, 4).toString());
                }
            }
        });
    }

    // ══════════════════════════════════════════════════════════════
    //  OPERACIONES CRUD
    // ══════════════════════════════════════════════════════════════

    private void guardarUsuario() {
        if (!validarCampos()) return;
        Usuario u = new Usuario(
            txtNombre.getText().trim(),
            txtApellido.getText().trim(),
            txtEmail.getText().trim(),
            txtTelefono.getText().trim()
        );
        if (dao.insertar(u)) {
            JOptionPane.showMessageDialog(this, "✅ Usuario guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarUsuario() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;
        Usuario u = new Usuario(
            Integer.parseInt(txtId.getText()),
            txtNombre.getText().trim(),
            txtApellido.getText().trim(),
            txtEmail.getText().trim(),
            txtTelefono.getText().trim()
        );
        if (dao.actualizar(u)) {
            JOptionPane.showMessageDialog(this, "✅ Usuario actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarUsuario() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "✅ Usuario eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Usuario> lista = dao.listarTodos();
        for (Usuario u : lista) {
            modeloTabla.addRow(new Object[]{
                u.getId(), u.getNombre(), u.getApellido(), u.getEmail(), u.getTelefono()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        tabla.clearSelection();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
            txtApellido.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos Nombre, Apellido y Email son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // ══════════════════════════════════════════════════════════════
    //  Helpers para crear componentes
    // ══════════════════════════════════════════════════════════════

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(60, 80, 120));
        return lbl;
    }

    private JTextField crearCampo(boolean soloLectura) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Arial", Font.PLAIN, 12));
        tf.setEditable(!soloLectura);
        if (soloLectura) tf.setBackground(new Color(240, 240, 240));
        tf.setPreferredSize(new Dimension(230, 28));
        return tf;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setOpaque(true);                    // CLAVE: fuerza el color de fondo
        btn.setBorderPainted(false);            // quita borde nativo que bloquea el color
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(230, 34));
        return btn;
    }

    // ══════════════════════════════════════════════════════════════
    //  Main
    // ══════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try {
            // Nimbus respeta los colores personalizados; el L&F del sistema los sobreescribe
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new UsuarioForm().setVisible(true));
    }
}
