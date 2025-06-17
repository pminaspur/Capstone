
// Weight Model : This Class is created to keep record of the daily weight and date details of the user.
// //Modified By : Pushpa Laxman
//  Modified Date: 12/14/2024

package com.zybooks.mytrackingapp;

public class WeightModel {
    private long weightId;
    private String date;
    private float daily_weight;

    public long getWeightId() {
        return weightId;
    }

    public void setWeightId(long weightId) {
        this.weightId = weightId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getDaily_weight() {
        return daily_weight;
    }

    public void setDaily_weight(float daily_weight) {
        this.daily_weight = daily_weight;
    }
}
