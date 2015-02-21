package com.mycompany.festivalhackmusic;

/**
 * Created by Andon on 20/02/2015.
 */
public class Track
{
    public String getSevenDigTrackId() {
        return SevenDigTrackId;
    }

    public void setSevenDigTrackId(String sevenDigTrackId) {
        SevenDigTrackId = sevenDigTrackId;
    }

    public String getSevenDigArtistId() {
        return SevenDigArtistId;
    }

    public void setSevenDigArtistId(String sevenDigArtistId) {
        SevenDigArtistId = sevenDigArtistId;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public float getDancability() {
        return Dancability;
    }

    public void setDancability(float dancability) {
        Dancability = dancability;
    }

    public float getEnergy() {
        return Energy;
    }

    public void setEnergy(float energy) {
        Energy = energy;
    }

    public float getTempo() {
        return Tempo;
    }

    public void setTempo(float tempo) {
        Tempo = tempo;
    }

    public String SevenDigTrackId;
    public String SevenDigArtistId;
    public String Artist;
    public float Dancability;
    public float Energy;
    public float Tempo;

    public Track()
    {

    }

    public Track(String sevenDigTrackId, String sevenDigArtistId, String artist, float dancability, float energy, float tempo)
    {
        this.Artist = artist;
        this.Dancability = dancability;
        this.Energy = energy;
        this.SevenDigArtistId = sevenDigArtistId;
        this.SevenDigTrackId = sevenDigTrackId;
        this.Tempo = tempo;
    }


}
