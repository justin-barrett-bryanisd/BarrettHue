
/**
 *
 * @author NAME
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class HueMain implements ActionListener, KeyListener, MouseListener {

    javax.swing.Timer timer;
    JFrame frame;
    JPanel display;
    GridInformation gi;
    int row, col, width, height;
    boolean first;
    int rowSelected, colSelected;
    HashSet<Point> blocked;
    int moves;

    public static void main(String[] args) throws Exception {
        new HueMain();
    }

    public HueMain() {
        frame = new JFrame("Insert Title Here");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        display = new DisplayPanel();
        frame.add(display);
        //put constructor code here
        String[] options = {"3x3", "5x5", "7x7", "10x10", "20x20", "25x25", "30x40"};
        JComboBox optionList = new JComboBox(options);
        JOptionPane.showMessageDialog(null, optionList);
        moves = 0;
        row = 20;
        col = 25;
        try {
            Scanner scan = new Scanner(((String) optionList.getSelectedItem()).replaceAll("x", " "));
            row = scan.nextInt();
            col = scan.nextInt();
        } catch (Exception e) {
        }
        width = 100;
        height = 100;
        blocked = new HashSet<Point>();
        blocked.add(new Point(0, 0));
        blocked.add(new Point(row - 1, col - 1));
        blocked.add(new Point(0, col - 1));
        blocked.add(new Point(row - 1, 0));
        Random random = new Random();
        for (int i = 0; i < row * col / 26; i++) {
            blocked.add(new Point(random.nextInt(row), random.nextInt(col)));
        }
        gi = new GridInformation(row, col, blocked);
        gi.createPuzzle();
        first = false;
        rowSelected = colSelected = 0;

        //end your constructor code
        timer = new javax.swing.Timer(10, this);
        timer.start();
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //type what needs to be performed every time the timer ticks
        height = (int) Math.ceil(1.0 * display.getHeight() / row);
        width = (int) Math.ceil(1.0 * display.getWidth() / col);
        //end your code for timer tick code
        if (gi.solved()) {
            display.repaint();
            JOptionPane.showMessageDialog(null, "You Win!\nTotal Moves: " + moves);
            
            System.exit(0);
        }
        display.repaint();
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (blocked.contains(new Point(e.getY() / height, e.getX() / width))) {
            System.out.println("Blocked");
            return;
        }
        if (!first) {
            first = true;
            rowSelected = e.getY() / height;
            colSelected = e.getX() / width;
        } else {
            first = false;
            gi.swapColors(rowSelected, colSelected, e.getY() / height, e.getX() / width);
            moves++;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    class DisplayPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //draw your graphics here
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    g.setColor(gi.getColor(r, c));
                    g.fillRect(c * width, r * height, width, height);
                }
            }
            g.setColor(Color.white);
            for (Point point : blocked) {
                g.fillRect(point.y * width + width * 3 / 10 + 1, point.x * height + height * 3 / 10 + 1, width * 2 / 5, height * 2 / 5);
            }
            if (first) {
                for (int i = 0; i < width / 20 + 1; i++) {

                    g.drawRect(colSelected * width + i, rowSelected * height + i, width - 2 * i, height - 2 * i);
                }
            }

        }
    }
}
