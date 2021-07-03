package com.example.google_map;

public class CoffeeHouse {
    public String name;
    public double lat;
    public double lng;
    public String url;
    public String detail;
    public String avatar;

    public CoffeeHouse(String name, double lat, double lng, String url, String detail, String avatar) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.url = url;
        this.detail = detail;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getUrl() {
        return url;
    }

    public String getDetail() {
        return detail;
    }

    public String getAvatar() {
        return avatar;
    }

}
