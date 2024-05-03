import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NumberLinePlotSwing extends JPanel {
    private ArrayList<ArrayList<Double>> clusters;
    private int minValue = 0;  // Minimum value of the number line
    private int maxValue = 1000; // Maximum value of the number line

    ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.BLUE, Color.GREEN, Color.RED, Color.ORANGE, Color.BLACK, Color.MAGENTA, Color.YELLOW, Color.CYAN));

    public NumberLinePlotSwing(ArrayList<ArrayList<Double>> clusters, int minValue, int maxValue) {
        this.clusters = clusters;
        this.minValue = minValue;
        this.maxValue = (((maxValue + 9) / 10) * 10);
        System.out.println("Max Value: " + maxValue);
        setPreferredSize(new Dimension(800, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the number line
        g.drawLine(50, 150, 750, 150);

        // Drawing numbers on the number line
        int numberOfMarks = 10; // You can adjust this based on how many marks you want
        int range = maxValue - minValue;
        for (int i = 0; i <= numberOfMarks; i++) {
            int x = 50 + i * (700 / numberOfMarks); // Calculate position for each mark
            g.drawLine(x, 145, x, 155); // Draw small tick mark
            int label = minValue + (range * i / numberOfMarks);
            g.drawString(Integer.toString(label), x - 10, 170); // Draw number below the line
        }

        // Plot points for each ArrayList in clusters
        for (int i = 0; i < clusters.size(); i++) {
            plotPoints(g, clusters.get(i), colors.get(i), range);
        }
    }

    private void plotPoints(Graphics g, ArrayList<Double> list, Color color, int range) {
        g.setColor(color);
        for (Double value : list) {
            int x = 50 + (int)((value - minValue) / (double) range * 700); // Adjusted scaling to fit the panel width
            g.fillOval(x, 145, 10, 10); // Draw circle with radius 5
        }
    }

    public static void createNumberLine(ArrayList<ArrayList<Double>> clusters, int minValue, int maxValue) {
        JFrame frame = new JFrame("Number Line Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new NumberLinePlotSwing(clusters, minValue, maxValue));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

}
