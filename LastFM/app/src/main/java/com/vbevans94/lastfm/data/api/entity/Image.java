package com.vbevans94.lastfm.data.api.entity;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("#text")
    private final String url;

    private final Size size;

    public Image(String url, Size size) {
        this.url = url;
        this.size = size;
    }

    public Image() {
        this(null, null);
    }

    public String getUrl() {
        return url;
    }

    public Size getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (url != null ? !url.equals(image.url) : image.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                ", size=" + size +
                '}';
    }

    public enum Size {

        small, medium, large, extralarge
    }
}
