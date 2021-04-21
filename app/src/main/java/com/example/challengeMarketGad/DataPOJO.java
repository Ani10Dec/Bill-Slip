package com.example.challengeMarketGad;

public class DataPOJO {
    private String id;
    private String image;
    private String amount;

    public DataPOJO(String id, String image, String amount) {
        this.id = id;
        this.image = image;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
