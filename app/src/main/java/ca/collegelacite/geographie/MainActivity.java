package ca.collegelacite.geographie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {


    static private final String TAG = "MainActivity";


    private ArrayList<Pays> listeDonnees;
    private ImageView countryiamge;
    private ListView myListView;
    private TextView coutrycapitable,countrypopulation,countrysuperfice,countryName;
    private Toolbar toolbar;

    private String name,capitale,superficie,imagelink,coutrylink;
    private int population;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        setSupportActionBar(toolbar);


        listeDonnees = Pays.lireDonnées ("afrique.json", this);
        ArrayAdapter<Pays> adapter=new ArrayAdapter<Pays>(this, android.R.layout.simple_list_item_1,listeDonnees);
        myListView.setAdapter(adapter);
        loadDefaultData(); //this methode load first country as default
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                name=listeDonnees.get(position).getNom();
                capitale=listeDonnees.get(position).getCapitale();
                population=listeDonnees.get(position).getPopulation();
                superficie=listeDonnees.get(position).getSuperficie();
                imagelink=listeDonnees.get(position).getImageUrl();
                coutrylink=listeDonnees.get(position).getWikiUrl();

                coutrycapitable.setText(capitale);
                countrypopulation.setText(String.valueOf(population));
                countrysuperfice.setText(superficie);
                countryName.setText(name);


                Picasso.get().load(imagelink).error(R.drawable.error_icon)
                        .into(countryiamge, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d(TAG, "onError: "+e.toString());
                            }
                        });





            }
        });

        if (savedInstanceState!=null)
        {
            name=savedInstanceState.getString("name");
            superficie=savedInstanceState.getString("superficie");
            population=savedInstanceState.getInt("population");
            imagelink=savedInstanceState.getString("imagelink");
            coutrylink=savedInstanceState.getString("coutrylink");
            capitale=savedInstanceState.getString("capitale");

            countryName.setText(name);
            coutrycapitable.setText(capitale);
            countrypopulation.setText(String.valueOf(population));
            countrysuperfice.setText(superficie);


            Picasso
                    .get().load(imagelink).error(R.drawable.error_icon)
                    .into(countryiamge);


        }



    }

    private void loadDefaultData() {
        name=listeDonnees.get(0).getNom();
        capitale=listeDonnees.get(0).getCapitale();
        population=listeDonnees.get(0).getPopulation();
        superficie=listeDonnees.get(0).getSuperficie();
        imagelink=listeDonnees.get(0).getImageUrl();
        coutrylink=listeDonnees.get(0).getWikiUrl();


        coutrycapitable.setText(capitale);
        countrypopulation.setText(String.valueOf(population));
        countrysuperfice.setText(superficie);
        countryName.setText(name);


        Picasso
                .get().load(imagelink).error(R.drawable.error_icon)
                .into(countryiamge);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name",name);
        outState.putString("superficie",superficie);
        outState.putInt("population",population);
        outState.putString("imagelink",imagelink);
        outState.putString("coutrylink",coutrylink);
        outState.putString("capitale",capitale);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.wiki)
        {

            goToUrl();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToUrl() {

        String url = coutrylink;
        Uri uri=Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initWidget() {
        coutrycapitable=findViewById(R.id.Capitale);
        countrypopulation=findViewById(R.id.Population);
        countrysuperfice=findViewById(R.id.Superfice);
        myListView=findViewById(R.id.listOfCountries);
        countryName=findViewById(R.id.countryName);
        countryiamge=findViewById(R.id.countryImage);
        toolbar=findViewById(R.id.toolbar);
    }

}