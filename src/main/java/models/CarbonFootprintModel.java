
package models;

public class CarbonFootprintModel {
    // Updated Emission factors specific to India
    private static final double CO2_PER_KM_CAR = 0.18; // grams per km (considering Indian car emissions)
    private static final double CO2_PER_KM_PUBLIC_TRANSPORT = 0.04; // grams per km (averaging buses, metros)
    private static final double CO2_PER_FLIGHT = 90.0; // per domestic flight in India
    private static final double CO2_PER_KWH = 0.82; // kg per kWh (India’s grid intensity)
    private static final double CO2_PER_LPG_CYLINDER = 42.5; // kg per LPG cylinder in India
    private static final double CO2_PER_KG_WASTE = 0.5; // kg CO2 per kg waste (India)
    private static final double CO2_PER_SERVING_MEAT = 3.3; // kg per serving (more plant-based diet in India)
    private static final double CO2_PER_SERVING_DAIRY = 0.7; // kg per serving (considering local production)

    // User input
    private double carTravel, publicTransport, flights, electricity, lpgUsage, waste, meatConsumption, dairyConsumption;
    private boolean buyLocal;

    public CarbonFootprintModel(double carTravel, double publicTransport, double flights, double electricity, double lpgUsage, double waste, double meatConsumption, double dairyConsumption, boolean buyLocal) {
        this.carTravel = carTravel;
        this.publicTransport = publicTransport;
        this.flights = flights;
        this.electricity = electricity;
        this.lpgUsage = lpgUsage;
        this.waste = waste;
        this.meatConsumption = meatConsumption;
        this.dairyConsumption = dairyConsumption;
        this.buyLocal = buyLocal;
    }

    public double calculateTotalFootprint() {
        double transportationFootprint =
                (carTravel * CO2_PER_KM_CAR) +
                        (publicTransport * CO2_PER_KM_PUBLIC_TRANSPORT) +
                        ((flights / 12) * CO2_PER_FLIGHT);

        double householdFootprint =
                (electricity * CO2_PER_KWH) +
                        (lpgUsage * CO2_PER_LPG_CYLINDER) +
                        (waste * CO2_PER_KG_WASTE);

        double lifestyleFootprint =
                (meatConsumption * 4 * CO2_PER_SERVING_MEAT) +
                        (dairyConsumption * 4 * CO2_PER_SERVING_DAIRY);

        if (buyLocal) lifestyleFootprint *= 0.9;

        return transportationFootprint + householdFootprint + lifestyleFootprint;
    }

}