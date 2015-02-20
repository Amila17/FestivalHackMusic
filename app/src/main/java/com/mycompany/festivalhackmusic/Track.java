package com.mycompany.festivalhackmusic;

/**
 * Created by Andon on 20/02/2015.
 */
public class Track {
    public String TrackID;
    public String ArtistID;
    public String Artist;
    public float dancability;
    public float energy;
    public float Tempo;

    public Track(     String TrackID,
             String ArtistID,
             String Artist,
             float dancability,
             float energy,
             float Tempo){
        this.Tempo = Tempo;
        this.energy = energy;
        this.dancability = dancability;
        this.Artist = Artist;
        this.ArtistID = ArtistID;
        this.TrackID = TrackID;
    }
}
