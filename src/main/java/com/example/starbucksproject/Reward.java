package com.example.starbucksproject;

public class Reward {
    private int starCost;
    private boolean redeemed;

    public Reward(int starCost) {
        this.starCost = starCost;
        this.redeemed = false;
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