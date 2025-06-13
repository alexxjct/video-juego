import java.awt.*;

public class Tank {
    private double x, y;
    private double velocidad = 3.0;
    private Color color;
    private String nombre;
    private int vida = 100;
    private int ancho = 40, alto = 30;
    private long ultimoDisparo = 0;
    private long cooldownDisparo = 500;
    private int direccion; // 1 = derecha, -1 = izquierda

    public Tank(double x, double y, Color color, String nombre, int direccion) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public void mover(double dx, double dy) {
        if (vida <= 0) return;
        double nuevaX = x + dx * velocidad;
        double nuevaY = y + dy * velocidad;
        if (nuevaX >= 0 && nuevaX <= 800 - ancho) x = nuevaX;
        if (nuevaY >= 0 && nuevaY <= 600 - alto) y = nuevaY;
    }

    public Proyectil disparar() {
        if (vida <= 0) return null;
        long tiempoActual = System.currentTimeMillis();
        if (tiempoActual - ultimoDisparo < cooldownDisparo) return null;
        ultimoDisparo = tiempoActual;
        double centroY = y + alto / 2.0;
        double salidaX = (direccion == 1) ? x + ancho : x;
        return new Proyectil(salidaX, centroY, this, direccion);
    }

    public void dibujar(Graphics2D g) {
        if (vida <= 0) return;
        g.setColor(color);
        g.fillRect((int)x, (int)y, ancho, alto);
        g.setColor(Color.BLACK);
        g.drawRect((int)x, (int)y, ancho, alto);
        g.setColor(color.darker());
        if (direccion == 1) {
            g.fillRect((int)x + ancho, (int)y + alto/2 - 2, 15, 4);
        } else {
            g.fillRect((int)x - 15, (int)y + alto/2 - 2, 15, 4);
        }
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y - 10, ancho, 5);
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y - 10, (int)(ancho * vida / 100.0), 5);
    }

    public boolean colisionaCon(Proyectil proyectil) {
        return proyectil.getX() >= x && proyectil.getX() <= x + ancho &&
               proyectil.getY() >= y && proyectil.getY() <= y + alto;
    }

    public void recibirDano(int dano) {
        vida = Math.max(0, vida - dano);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getVida() { return vida; }
    public String getNombre() { return nombre; }
    public int getDireccion() { return direccion; }
}