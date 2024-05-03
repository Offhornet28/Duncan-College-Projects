import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.JSONObject;

public class GUI implements ActionListener {
    int count = 0;
    private static String BusinessID;

    private JLabel InputLabel, UserBusLabel, UserBusinessCategories, UserBusStarValue;
    private JLabel SimilarBusLabel1, SimilarBusCategoriesLabel1, SimilarityScoreLabel1, SimilarBusStarValue1;

    private JLabel SimilarBusLabel2, SimilarBusCategoriesLabel2, SimilarityScoreLabel2, SimilarBusStarValue2;
    private JTextField textField;
    private JFrame frame;
    private JPanel panel;

    private JSONObject userBusiness;

    private JSONObject mostSimilarBusiness;
    private JSONObject secondMostSimilarBusiness;

    public GUI() {
        frame = new JFrame("Similarity Metric Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);



        //______________________________Enter Business Label______________________________


        InputLabel = new JLabel(" Enter Business ID: ");
        InputLabel.setBounds(50, 50,  200,  30);
        InputLabel.setOpaque(true);
        InputLabel.setBackground(Color.white);
        InputLabel.setForeground(Color.BLACK);

        // Set the label's font size to 20
        InputLabel.setFont(new Font(InputLabel.getFont().getName(), InputLabel.getFont().getStyle(), 20));


        //______________________________User Business TextInput______________________________

        textField = new JTextField(40);
        textField.setBounds(250, 50, 300,  31);
        textField.setFont(new Font(textField.getFont().getName(), textField.getFont().getStyle(), 20));


        //______________________________Search Button______________________________


        JButton button = new JButton("Search Database");
        button.addActionListener(this);
        button.setBounds(600, 45, 200,  40);
        button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), 20));


        //______________________________User Business Label________________________________________________________________________________________________________________________


        UserBusLabel = new JLabel("User Business Name:");
        UserBusLabel.setBounds(50, 125,  800,  30);
        UserBusLabel.setOpaque(true);
        UserBusLabel.setBackground(new Color(118, 132, 181));
        UserBusLabel.setForeground(Color.BLACK);

        UserBusLabel.setFont(new Font(UserBusLabel.getFont().getName(), UserBusLabel.getFont().getStyle(), 20));


        //______________________________User Business Categories Label______________________________


        UserBusinessCategories = new JLabel(" Categories:");
        UserBusinessCategories.setBounds(50, 175,  1300,  30);
        UserBusinessCategories.setOpaque(true);
        UserBusinessCategories.setBackground(Color.WHITE);
        UserBusinessCategories.setForeground(Color.BLACK);

        UserBusinessCategories.setFont(new Font(UserBusinessCategories.getFont().getName(), UserBusinessCategories.getFont().getStyle(), 20));


        //______________________________User Business Star Label______________________________


        UserBusStarValue = new JLabel(" Stars:");
        UserBusStarValue.setBounds(50, 225,  100,  30);
        UserBusStarValue.setOpaque(true);
        UserBusStarValue.setBackground(Color.WHITE);
        UserBusStarValue.setForeground(Color.BLACK);

        UserBusStarValue.setFont(new Font(UserBusStarValue.getFont().getName(), UserBusStarValue.getFont().getStyle(), 20));





        //********************************************************************************Similar Business Label 1********************************************************************************



        SimilarBusLabel1 = new JLabel("Most Similar Business:");
        SimilarBusLabel1.setBounds(50, 300,  900,  30);
        SimilarBusLabel1.setOpaque(true);
        SimilarBusLabel1.setBackground(Color.LIGHT_GRAY);
        SimilarBusLabel1.setForeground(Color.BLACK);

        // Set the label's font size to 20
        SimilarBusLabel1.setFont(new Font(SimilarBusLabel1.getFont().getName(), SimilarBusLabel1.getFont().getStyle(), 20));


        //______________________________Similar Business Categories Label______________________________


        SimilarBusCategoriesLabel1 = new JLabel(" Categories:");
        SimilarBusCategoriesLabel1.setBounds(50, 350,  1300,  30);
        SimilarBusCategoriesLabel1.setOpaque(true);
        SimilarBusCategoriesLabel1.setBackground(Color.WHITE);
        SimilarBusCategoriesLabel1.setForeground(Color.BLACK);

        SimilarBusCategoriesLabel1.setFont(new Font(SimilarBusCategoriesLabel1.getFont().getName(), SimilarBusCategoriesLabel1.getFont().getStyle(), 20));

        //______________________________Similar Business Star Label______________________________


        SimilarBusStarValue1 = new JLabel(" Stars:");
        SimilarBusStarValue1.setBounds(50, 400,  100,  30);
        SimilarBusStarValue1.setOpaque(true);
        SimilarBusStarValue1.setBackground(Color.WHITE);
        SimilarBusStarValue1.setForeground(Color.BLACK);

        SimilarBusStarValue1.setFont(new Font(SimilarBusStarValue1.getFont().getName(), SimilarBusStarValue1.getFont().getStyle(), 20));

        //______________________________Similarity Score Label______________________________


        SimilarityScoreLabel1 = new JLabel(" SimilarityScore:");
        SimilarityScoreLabel1.setBounds(250, 400,  1100,  30);
        SimilarityScoreLabel1.setOpaque(true);
        SimilarityScoreLabel1.setBackground(Color.WHITE);
        SimilarityScoreLabel1.setForeground(Color.BLACK);

        SimilarityScoreLabel1.setFont(new Font(SimilarityScoreLabel1.getFont().getName(), SimilarityScoreLabel1.getFont().getStyle(), 20));


        //****************************************Similar Business Label 2****************************************



        SimilarBusLabel2 = new JLabel("Second Most Similar Business:");
        SimilarBusLabel2.setBounds(50, 475,  800,  30);
        SimilarBusLabel2.setOpaque(true);
        SimilarBusLabel2.setBackground(Color.LIGHT_GRAY);
        SimilarBusLabel2.setForeground(Color.BLACK);

        // Set the label's font size to 20
        SimilarBusLabel2.setFont(new Font(SimilarBusLabel2.getFont().getName(), SimilarBusLabel2.getFont().getStyle(), 20));


        //______________________________Similar Business Categories Label______________________________


        SimilarBusCategoriesLabel2 = new JLabel(" Categories:");
        SimilarBusCategoriesLabel2.setBounds(50, 525,  1300,  30);
        SimilarBusCategoriesLabel2.setOpaque(true);
        SimilarBusCategoriesLabel2.setBackground(Color.WHITE);
        SimilarBusCategoriesLabel2.setForeground(Color.BLACK);

        SimilarBusCategoriesLabel2.setFont(new Font(SimilarBusCategoriesLabel2.getFont().getName(), SimilarBusCategoriesLabel2.getFont().getStyle(), 20));

        //______________________________Similar Business Star Label______________________________


        SimilarBusStarValue2 = new JLabel(" Stars:");
        SimilarBusStarValue2.setBounds(50, 575,  100,  30);
        SimilarBusStarValue2.setOpaque(true);
        SimilarBusStarValue2.setBackground(Color.WHITE);
        SimilarBusStarValue2.setForeground(Color.BLACK);

        SimilarBusStarValue2.setFont(new Font(SimilarBusStarValue2.getFont().getName(), SimilarBusStarValue2.getFont().getStyle(), 20));

        //______________________________Similarity Score Label______________________________


        SimilarityScoreLabel2 = new JLabel("SimilarityScore:");
        SimilarityScoreLabel2.setBounds(250, 575,  1100,  30);
        SimilarityScoreLabel2.setOpaque(true);
        SimilarityScoreLabel2.setBackground(Color.WHITE);
        SimilarityScoreLabel2.setForeground(Color.BLACK);

        SimilarityScoreLabel2.setFont(new Font(SimilarityScoreLabel2.getFont().getName(), SimilarityScoreLabel2.getFont().getStyle(), 20));






        // JPanel settings with null layout
        panel = new JPanel(null);
        panel.setBounds(0, 0, 2000, 1500);
        panel.add(InputLabel);
        panel.setOpaque(true);
        panel.setBackground(new Color(173, 216, 230));
        panel.add(textField);
        panel.add(button);
        panel.add(UserBusinessCategories);
        panel.add(UserBusLabel);
        panel.add(UserBusStarValue);

        //Similar Business 1
        panel.add(SimilarBusLabel1);
        panel.add(SimilarBusCategoriesLabel1);
        panel.add(SimilarityScoreLabel1);
        panel.add(SimilarBusStarValue1);

        //Similar Business 2
        panel.add(SimilarBusLabel2);
        panel.add(SimilarBusCategoriesLabel2);
        panel.add(SimilarityScoreLabel2);
        panel.add(SimilarBusStarValue2);



        // Add the panel to the frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Similarity GUI");
        frame.setSize(2000, 1500); // Set the size of the frame
        frame.setLocationRelativeTo(null); // Center the frame

        frame.setVisible(true);
    }

    public void UpdateUserBusCategories()
    {
        System.out.println("\nFoundObject Name:   " + userBusiness.getString("name"));
        UserBusLabel.setText("User Business Name:   " + userBusiness.getString("name"));
        UserBusinessCategories.setText("Categories:   " + userBusiness.getString("categories"));
        UserBusStarValue.setText("Stars: " + userBusiness.getDouble("stars"));

    }
    public void UpdateMostSimilarBus()
    {
        SimilarBusLabel1.setText("Similar Business Name:   " + mostSimilarBusiness.getString("name"));
        SimilarBusCategoriesLabel1.setText("Categories:   " + mostSimilarBusiness.getString("categories"));
        SimilarBusStarValue1.setText("Stars: " + mostSimilarBusiness.getDouble("stars"));
    }

    public void UpdateMostSimilarBus2()
    {
        SimilarBusLabel2.setText("Second Most Similar Business:   " + secondMostSimilarBusiness.getString("name"));
        SimilarBusCategoriesLabel2.setText("Categories:   " + secondMostSimilarBusiness.getString("categories"));
        SimilarBusStarValue2.setText("Stars: " + secondMostSimilarBusiness.getDouble("stars"));
    }

    public void UpdateSimilarityRatings()
    {
        double similarityScore = Loader.getSimilarityScore();
        SimilarityScoreLabel1.setText("Similarity: " + similarityScore);

        double similarityScore2 = Loader.getSimilarityScore2();
        SimilarityScoreLabel2.setText("Similarity: " + similarityScore2);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        BusinessID = textField.getText();

        if(!BusinessID.isEmpty()) {
            System.out.println("BusinessID: " + BusinessID);
            JSONArray returnArray = SimilarityMetric_TFIDF.runSimilarityMetric(BusinessID);
            if(returnArray != null)
            {
                //No Error:
                userBusiness = returnArray.getJSONObject(0);
                mostSimilarBusiness = returnArray.getJSONObject(1);
                secondMostSimilarBusiness = returnArray.getJSONObject(2);

                //Updating User Bus
                UpdateUserBusCategories();

                //Updating Similar Bus
                UpdateMostSimilarBus();

                UpdateMostSimilarBus2();

                //Update SimilarityRating
                UpdateSimilarityRatings();
            }
            else
            {
                //Could not find business
                System.out.println("Error");
            }
        } else {
            InputLabel.setText("Enter A Valid BusinessID: ");
        }
        */
    }

    /*

    public static void main(String[] args) {
        new GUI();
    }

     */
}
