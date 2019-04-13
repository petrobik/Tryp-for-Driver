package com.rdev.trypfordriver.data.model;

import java.util.List;
import java.util.Map;

public class FirebaseClient {

    private int id;

    private String last_name;

    private String first_name;

    private int stars;

    private String photo;

    private List<FirebaseFeedback> feedbacks;

    public FirebaseClient() {
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public int getStars() {
        return stars;
    }

    public String getPhoto() {
        return photo;
    }

    public List<FirebaseFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setFeedbacks(List<FirebaseFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
