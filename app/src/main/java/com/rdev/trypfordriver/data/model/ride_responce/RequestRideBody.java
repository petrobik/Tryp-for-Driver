package com.rdev.trypfordriver.data.model.ride_responce;

public class RequestRideBody {
    private String to_lat;
    private String driver_id;
    private String from_lng;
    private String user_id;
    private String from;
    private String to_lng;
    private String to;
    private String pickup_time;
    private String ip_address;
    private String asap;
    private String from_lat;

    public void setTo_lat(String to_lat){
        this.to_lat = to_lat;
    }

    public RequestRideBody(String toLat, String driverId, String from_lng, String userId, String from, String to_lng, String to, String pickup_time, String ipAddress, String asap, String from_lat) {
        this.to_lat = toLat;
        this.driver_id = driverId;
        this.from_lng = from_lng;
        this.user_id = userId;
        this.from = from;
        this.to_lng = to_lng;
        this.to = to;
        this.pickup_time = pickup_time;
        this.ip_address = ipAddress;
        this.asap = asap;
        this.from_lat = from_lat;
    }

    public String getTo_lat(){
        return to_lat;
    }

    public void setDriver_id(String driver_id){
        this.driver_id = driver_id;
    }

    public String getDriver_id(){
        return driver_id;
    }

    public void setFrom_lng(String from_lng){
        this.from_lng = from_lng;
    }

    public String getFrom_lng(){
        return from_lng;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setFrom(String from){
        this.from = from;
    }

    public String getFrom(){
        return from;
    }

    public void setTo_lng(String to_lng){
        this.to_lng = to_lng;
    }

    public String getTo_lng(){
        return to_lng;
    }

    public void setTo(String to){
        this.to = to;
    }

    public String getTo(){
        return to;
    }

    public void setPickup_time(String pickup_time){
        this.pickup_time = pickup_time;
    }

    public String getPickup_time(){
        return pickup_time;
    }

    public void setIp_address(String ip_address){
        this.ip_address = ip_address;
    }

    public String getIp_address(){
        return ip_address;
    }

    public void setAsap(String asap){
        this.asap = asap;
    }

    public String getAsap(){
        return asap;
    }

    public void setFrom_lat(String from_lat){
        this.from_lat = from_lat;
    }

    public String getFrom_lat(){
        return from_lat;
    }

    @Override
    public String toString(){
        return
                "RequideRestBody{" +
                        "to_lat = '" + to_lat + '\'' +
                        ",driver_id = '" + driver_id + '\'' +
                        ",from_lng = '" + from_lng + '\'' +
                        ",user_id = '" + user_id + '\'' +
                        ",from = '" + from + '\'' +
                        ",to_lng = '" + to_lng + '\'' +
                        ",to = '" + to + '\'' +
                        ",pickup_time = '" + pickup_time + '\'' +
                        ",ip_address = '" + ip_address + '\'' +
                        ",asap = '" + asap + '\'' +
                        ",from_lat = '" + from_lat + '\'' +
                        "}";
    }
}
