package ac.id.atmaluhur.aplikasistudiomusikpangkalpinang.model;

import com.google.gson.annotations.SerializedName;

public class LocationModel {
    public String getImageLocationName() {
        return imageLocationName;
    }

    public void setImageLocationName(String imageLocationName) {
        this.imageLocationName = imageLocationName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    @SerializedName("nama")
    private String imageLocationName;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longtitude")
    private String longtitude;

    public LocationModel(String imageLocationName, String latitude, String longtitude) {
        this.imageLocationName = imageLocationName;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
}
