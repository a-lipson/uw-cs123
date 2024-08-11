package me.alipson.classifier;

// Alexandre Lipson
// 08/12/24
// CSE 123
// P4: Spam Classifier - Extra Credit
// TA: Daniel

import java.util.*;
import java.lang.reflect.*;

/**
 * Songs
 */
public class Song implements Classifiable {
    // possible features:
    // track_id,track_name,track_artist,track_popularity,track_album_id,track_album_name,track_album_release_date,playlist_name,playlist_id,playlist_genre,playlist_subgenre,danceability,energy,key,loudness,mode,speechiness,acousticness,instrumentalness,liveness,valence,tempo,duration_ms
    private Map<SongFeature, Double> songFeatures = new HashMap<>();
    private final Map<SongFeature, Double> PCA_LOADINGS = new HashMap<>();

    /**
    */
    private enum SongFeature {
        DANCEABILITY,
        ENERGY,
        KEY,
        LOUDNESS,
        SPEECHINESS,
        ACOUSTICNESS,
        INSTRUMENTALNESS,
        LIVENESS,
        VALENCE,
        TEMPO,
    }

    /**
     * 
     * @param danceability
     * @param energy
     * @param key
     * @param loudness
     * @param speechiness
     * @param acousticness
     * @param instrumentalness
     * @param liveness
     * @param valence
     * @param tempo
     */
    public Song(double danceability, double energy, double key, double loudness,
            double speechiness, double acousticness, double instrumentalness,
            double liveness, double valence, double tempo) {
        songFeatures.put(SongFeature.DANCEABILITY, danceability);
        songFeatures.put(SongFeature.ENERGY, energy);
        songFeatures.put(SongFeature.KEY, key);
        songFeatures.put(SongFeature.LOUDNESS, loudness);
        songFeatures.put(SongFeature.SPEECHINESS, speechiness);
        songFeatures.put(SongFeature.ACOUSTICNESS, acousticness);
        songFeatures.put(SongFeature.INSTRUMENTALNESS, instrumentalness);
        songFeatures.put(SongFeature.LIVENESS, liveness);
        songFeatures.put(SongFeature.VALENCE, valence);
        songFeatures.put(SongFeature.TEMPO, tempo);

        getPCALoadings();
    }

    /**
    */
    private void getPCALoadings() {
        PCA_LOADINGS.put(SongFeature.DANCEABILITY, 2.146331);
        PCA_LOADINGS.put(SongFeature.ENERGY, 1.558253);
        PCA_LOADINGS.put(SongFeature.KEY, 2.247847);
        PCA_LOADINGS.put(SongFeature.LOUDNESS, 1.566717);
        PCA_LOADINGS.put(SongFeature.SPEECHINESS, 1.976885);
        PCA_LOADINGS.put(SongFeature.INSTRUMENTALNESS, 2.146783);
        PCA_LOADINGS.put(SongFeature.ACOUSTICNESS, 2.278709);
        PCA_LOADINGS.put(SongFeature.LIVENESS, 2.472897);
        PCA_LOADINGS.put(SongFeature.VALENCE, 2.055800);
        PCA_LOADINGS.put(SongFeature.TEMPO, 2.088194);
    }

    /**
     * @return
     */
    public Set<String> getFeatures() {
        Set<String> lowerCaseEnumNames = new HashSet<String>();

        for (SongFeature feature : SongFeature.values())
            lowerCaseEnumNames.add(feature.name().toLowerCase());

        return lowerCaseEnumNames;
    }

    /**
     * @param feature
     * 
     * @return
     */
    public double get(String feature) {
        if (!getFeatures().contains(feature)) {
            throw new IllegalArgumentException(
                    String.format("Invalid feature [%s], not within possible features [%s]",
                            feature, getFeatures().toString()));
        }

        for (SongFeature songFeature : SongFeature.values())
            if (songFeature.name().equalsIgnoreCase(feature))
                return songFeatures.get(songFeature);

        return 0.; // should not reach this case, invalid feature caught in sentinel
    }

    /**
    */
    private double get(SongFeature feature) {
        return get(feature.name());
    }

    /**
     * Creates a Classifiable Object from the provided row of song data.
     *
     * @param row
     * 
     * @return
     */
    public static Classifiable toClassifiable(List<String> row) {
        double[] fs = new double[10];
        int[] indices = new int[] { 11, 12, 13, 14, 16, 17, 18, 19, 20, 21 };

        for (int i = 0; i < fs.length; i++) {
            fs[i] = Double.parseDouble(row.get(indices[i]));
        }

        return new Song(fs[0], fs[1], fs[2], fs[3], fs[4], fs[5], fs[6], fs[7], fs[8], fs[9]);
    }

    /**
    */
    public Split partition(Classifiable other) {
        if (!(other instanceof Song))
            throw new IllegalArgumentException("Provided 'other' not instance of Song.java");

        Song otherSong = (Song) other;

        SongFeature bestFeature = null;
        double highestWeightedDiff = 0;

        // find feature with largest difference.
        for (SongFeature feature : PCA_LOADINGS.keySet()) {
            double thisValue = this.get(feature);
            double otherValue = otherSong.get(feature);

            // weight with PCA loadings
            double weightedDiff = Math.abs(thisValue - otherValue) * PCA_LOADINGS.get(feature);

            if (weightedDiff > highestWeightedDiff) {
                bestFeature = feature;
                highestWeightedDiff = weightedDiff;
            }
        }

        if (bestFeature == null) {
            throw new IllegalArgumentException("No valid feature found for partitioning.");
        }

        double halfway = Split.midpoint(this.get(bestFeature), otherSong.get(bestFeature));

        return new Split(bestFeature.name(), halfway);
    }

}
