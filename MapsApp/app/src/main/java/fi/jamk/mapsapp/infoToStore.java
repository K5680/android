package fi.jamk.mapsapp;

/**
 * Created by Omistaja on 2.10.2017.
 */

class infoToStore {
    Float lat;
    Float lng;
    String nimi;
    String osoite;

    infoToStore(Float lat, Float lng, String nimi, String osoite) {
        this.lat = lat;
        this.lng = lng;
        this.nimi = nimi;
        this.osoite = osoite;
    }

    Float getLat() {
        return this.lat;
    }

    Float getLng() {
        return this.lng;
    }

    String getNimi() {
        return this.nimi;
    }

    String getOsoite() {
        return this.osoite;
    }
}