package wsb.sp_pwgp.tablica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class IBDrawingBoard extends JFrame {

    private final JPanel drawingPanel; // Główna tablica
    private final JPanel thumbnailPanel; // Panel miniatury
    private BufferedImage canvas; // Obraz głównej tablicy
    private static final int THUMBNAIL_WIDTH = 150;
    private static final int THUMBNAIL_HEIGHT = 150;

    public IBDrawingBoard() {
        setTitle("Tablica Rysunkowa z Miniaturą");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Tworzenie głównej tablicy
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, null);
            }
        };
        drawingPanel.setPreferredSize(new Dimension(800, 600));
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawOnCanvas(e.getX(), e.getY());
            }
        });

        // Tworzenie panelu miniatury
        thumbnailPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage thumbnail = createThumbnail();
                if (thumbnail != null) {
                    g.drawImage(thumbnail, 0, 0, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, null);
                }
            }
        };
        thumbnailPanel.setPreferredSize(new Dimension(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT));
        thumbnailPanel.setBackground(Color.LIGHT_GRAY);

        // Dodanie elementów do okna
        add(drawingPanel, BorderLayout.CENTER);
        add(thumbnailPanel, BorderLayout.SOUTH); // Miniatura na dole (możesz zmienić na BorderLayout.EAST)

        setVisible(true);
    }

    // Rysowanie na głównej tablicy
    private void drawOnCanvas(int x, int y) {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x - 5, y - 5, 10, 10); // Przykładowe rysowanie kółka
        g2d.dispose();
        drawingPanel.repaint();
        thumbnailPanel.repaint(); // Odśwież miniaturę
    }

    // Tworzenie miniatury
    private BufferedImage createThumbnail() {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        BufferedImage thumbnail = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = thumbnail.createGraphics();

        // Skalowanie obrazu
        g2d.drawImage(canvas, 0, 0, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, 0, 0, canvasWidth, canvasHeight, null);
        g2d.dispose();

        return thumbnail;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IBDrawingBoard::new);
    }
}

