package ca.collegelacite.geographie;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class Pays implements Comparable, Serializable {
    private String  nom;
    private String capitale;
    private Integer population;
    private String  superficie;
    private String  wikiUrl;
    private String  imageUrl;

    public Pays() {
    }

    public Pays(String nom, String superf, Integer pop,
                String cap, String wikiUrl, String imageUrl) {
        setNom(nom);
        setPopulation(pop);
        setSuperficie(superf);
        setCapitale(cap);
        setWikiUrl(wikiUrl);
        setImageUrl(imageUrl);
    }

    // Fonction de comparaison utilisée pour trier la liste après la
    // lecture du JSON.
    @Override
    public int compareTo(Object autre) {
        return this.getNom().compareTo(((Pays)autre).getNom());
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCapitale() {
        return capitale;
    }

    public void setCapitale(String capitale) {
        this.capitale = capitale;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    // Retourne une chaîne décrivant l'objet
    @Override
    public String toString() {
        return "" + this.getNom() ;
    }

    // Désérialiser une liste d'éléments d'un fichier JSON.
    public static ArrayList<Pays> lireDonnées(String nomFichier, Context ctx) {

        ArrayList<Pays> liste = new ArrayList<>();
        try {
            // Charger les données dans la liste.
            String jsonString = lireJson(nomFichier, ctx);
            JSONObject json   = new JSONObject(jsonString);
            JSONArray tous    = json.getJSONArray("Pays");

            // Lire chaque élément du fichier.
            for(int i = 0; i < tous.length(); i++){
                Pays p = new Pays();

                p.nom     = tous.getJSONObject(i).getString("Nom");
                p.population= tous.getJSONObject(i).getInt("Population");
                p.superficie = tous.getJSONObject(i).getString("Superficie");
                p.capitale = tous.getJSONObject(i).getString("Capitale");
                p.wikiUrl = tous.getJSONObject(i).getString("wikiUrl");
                p.imageUrl = tous.getJSONObject(i).getString("image");

                liste.add(p);
            }
        } catch (JSONException e) {
            // Une erreur s'est produite (on la journalise).
            e.printStackTrace();
            return null;
        }

        // Trier la liste.
        Collections.sort(liste);

        return liste;
    }

    // Retourne une balise lue d'un fichier JSON.
    private static String lireJson(String nomFichier, Context ctx) {
        String json = null;

        try {
            InputStream is = ctx.getAssets().open(nomFichier);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (java.io.IOException ex) {
            // Une erreur s'est produite (on la journalise).
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
