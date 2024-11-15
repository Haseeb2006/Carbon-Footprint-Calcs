package controllers;

import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import models.CarbonFootprintModel;

import java.awt.Desktop;
import java.net.URI;

public class CalculatorController {

    // Original UI elements
    @FXML private TextField carTravelField;
    @FXML private TextField publicTransportField;
    @FXML private TextField flightsField;
    @FXML private TextField electricityUsageField;
    @FXML private TextField lpgUsageField;
    @FXML private TextField wasteField;
    @FXML private TextField meatConsumptionField;
    @FXML private TextField dairyConsumptionField;
    @FXML private CheckBox buyLocalProduceCheckBox;
    @FXML private Label resultLabel;
    @FXML private VBox tipsContainer;
    @FXML private Label tipsLabel;
    @FXML private Hyperlink learnMoreLink;

    // New UI elements for added features
    @FXML private PieChart emissionPieChart;
    @FXML private ImageView earthImageView;
    @FXML private Button growTreesButton;

    public void initialize() {
        // Initialize the Earth image for "healing" effect
        earthImageView.setImage(new Image(getClass().getResource("/earth.png").toExternalForm()));

        // Hide the pie chart initially
        emissionPieChart.setVisible(true);
    }

    @FXML
    private void calculateFootprint() {
        try {
            // Parse input values
            double carTravel = Double.parseDouble(carTravelField.getText());
            double publicTransport = Double.parseDouble(publicTransportField.getText());
            double flights = Double.parseDouble(flightsField.getText());
            double electricity = Double.parseDouble(electricityUsageField.getText());
            double lpgUsage = Double.parseDouble(lpgUsageField.getText());
            double waste = Double.parseDouble(wasteField.getText());
            double meatConsumption = Double.parseDouble(meatConsumptionField.getText());
            double dairyConsumption = Double.parseDouble(dairyConsumptionField.getText());
            boolean buyLocal = buyLocalProduceCheckBox.isSelected();

            // Calculate total footprint
            CarbonFootprintModel model = new CarbonFootprintModel(carTravel, publicTransport, flights, electricity, lpgUsage, waste, meatConsumption, dairyConsumption, buyLocal);
            double totalFootprint = model.calculateTotalFootprint();

            resultLabel.setText(String.format("Estimated Carbon Footprint: %.2f kg CO₂ per year", totalFootprint));
            resultLabel.setStyle("-fx-text-fill: green;");

            // Display Tips
            displayTips(totalFootprint);

            // Update and show pie chart based on calculated data
            updatePieChart(carTravel, publicTransport, flights, electricity, lpgUsage);
        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter valid numbers for all fields.");
            resultLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void displayTips(double footprint) {
        tipsContainer.setVisible(true);
        StringBuilder tips = new StringBuilder();

        if (footprint > 2) {
            tips.append("Consider reducing your car travel and flying less. ").append("\n");
        }
        if (footprint > 3) {
            tips.append("Try to lower your electricity usage by switching off appliances when not in use. ").append("\n");
        }
        if (footprint > 10) {
            tips.append("Minimize your waste production and recycle where possible. ").append("\n");
        }
        if (footprint < 20 && !buyLocalProduceCheckBox.isSelected()) {
            tips.append("Buying local produce can help reduce emissions. ").append("\n");
        }

        tipsLabel.setText(tips.toString());
        learnMoreLink.setVisible(true);
    }

    @FXML
    private void openLearnMoreLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://web.umang.gov.in/landing/department/carbon-neutral.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePieChart(double carTravel, double publicTransport, double flights, double electricity, double lpgUsage) {
        emissionPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Car Travel", carTravel),
                new PieChart.Data("Public Transport", publicTransport),
                new PieChart.Data("Flights", flights),
                new PieChart.Data("Electricity", electricity),
                new PieChart.Data("LPG Usage", lpgUsage)
        ));
        emissionPieChart.setVisible(true);
    }

    @FXML
    private void onGrowTreesClicked() {
        // Rotate animation for Earth healing effect
        RotateTransition earthRotate = new RotateTransition(Duration.seconds(1), earthImageView);
        earthRotate.setByAngle(20);
        earthRotate.play();
    }
}
