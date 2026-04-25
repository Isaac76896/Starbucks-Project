package com.example.starbucksproject;



public class Reward {
    private String rewardId;
    private String description;
    private int starCost;
    private boolean redeemed;

    public Reward(String rewardId, String description, int starCost, boolean redeemed) {
        this.rewardId = rewardId;
        this.description = description;
        this.starCost = starCost;
        this.redeemed = redeemed;
    }

    /** setters and getters **/

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStarCost() {
        return starCost;
    }

    public void setStarCost(int starCost) {
        this.starCost = starCost;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }
}