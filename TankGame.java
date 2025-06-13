import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankGame extends JPanel implements ActionListener, KeyListener {
    private static final int ANCHO = 800;
    private static final int ALTO = 600;
    private Timer timer;
    private Tank tanque1, tanque2;
    private List<Proyectil> proyectiles;
    private boolean[] teclas;

    public TankGame() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.GREEN.darker());
        setFocusable(true);
        addKeyListener(this);

        tanque1 = new Tank(100, 300, Color.BLUE, "Tanque 1", 1);
        tanque2 = new Tank(650, 300, Color.RED, "Tanque 2", -1);

        proyectiles = new ArrayList<>();
        teclas = new boolean[256];

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        tanque1.dibujar(g2d);
        tanque2.dibujar(g2d);

        for (Proyectil p : proyectiles) {
            p.dibujar(g2d);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(tanque1.getNombre() + " - Vida: " + tanque1.getVida(), 10, 30);
        g2d.drawString(tanque2.getNombre() + " - Vida: " + tanque2.getVida(), 600, 30);

        if (tanque1.getVida() <= 0) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            g2d.drawString("¡TANQUE 2 GANA!", 200, 300);
        } else if (tanque2.getVida() <= 0) {
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            g2d.drawString("¡TANQUE 1 GANA!", 200, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        manejarInput();
        actualizarJuego();
        repaint();
    }

    private void manejarInput() {
        if (teclas[KeyEvent.VK_W]) tanque1.mover(0, -1);
        if (teclas[KeyEvent.VK_S]) tanque1.mover(0, 1);
        if (teclas[KeyEvent.VK_A]) tanque1.mover(-1, 0);
        if (teclas[KeyEvent.VK_D]) tanque1.mover(1, 0);
        if (teclas[KeyEvent.VK_SPACE]) {
            Proyectil p = tanque1.disparar();
            if (p != null) proyectiles.add(p);
        }

        if (teclas[KeyEvent.VK_UP]) tanque2.mover(0, -1);
        if (teclas[KeyEvent.VK_DOWN]) tanque2.mover(0, 1);
        if (teclas[KeyEvent.VK_LEFT]) tanque2.mover(-1, 0);
        if (teclas[KeyEvent.VK_RIGHT]) tanque2.mover(1, 0);
        if (teclas[KeyEvent.VK_ENTER]) {
            Proyectil p = tanque2.disparar();
            if (p != null) proyectiles.add(p);
        }
    }

    private void actualizarJuego() {
        for (int i = proyectiles.size() - 1; i >= 0; i--) {
            Proyectil p = proyectiles.get(i);
            p.actualizar();

            if (p.getX() < 0 || p.getX() > ANCHO || p.getY() < 0 || p.getY() > ALTO) {
                proyectiles.remove(i);
                continue;
            }

            if (p.getOwner() != tanque1 && tanque1.colisionaCon(p)) {
                tanque1.recibirDano(25);
                proyectiles.remove(i);
            } else if (p.getOwner() != tanque2 && tanque2.colisionaCon(p)) {
                tanque2.recibirDano(25);
                proyectiles.remove(i);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclas[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Batalla de Tanques");
        TankGame juego = new TankGame();

        frame.add(juego);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}