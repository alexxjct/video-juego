import java.awt.*;

public class Proyectil {
    private double x, y;
    private double velocidadX, velocidadY;
    private Tank owner;
    private static final int RADIO = 3;
    private static final double VELOCIDAD = 8.0;

    public Proyectil(double x, double y, Tank owner, int direccion) {
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.velocidadX = VELOCIDAD * direccion;
        this.velocidadY = 0;
    }

    public void actualizar() {
        x += velocidadX;
        y += velocidadY;
    }

    public void dibujar(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int)x - RADIO, (int)y - RADIO, RADIO * 2, RADIO * 2);
        g.setColor(Color.ORANGE);
        g.drawOval((int)x - RADIO, (int)y - RADIO, RADIO * 2, RADIO * 2);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public Tank getOwner() { return owner; }
}