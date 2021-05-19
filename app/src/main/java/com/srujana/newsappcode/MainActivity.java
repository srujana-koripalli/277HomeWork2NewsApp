package com.srujana.newsappcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.srujana.newsappcode.Models.Articles;
import com.srujana.newsappcode.Models.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editSearch;
    Button btnSearch;

    final String API_KEY="389db0d6eec0494d828a8222d15d8281";
    Adapter adapter;
    List <Articles> articles =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch =findViewById(R.id.editSearch);
        btnSearch=findViewById(R.id.btnSearch);


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String country=getCountry();
        retrieveJson("",country,API_KEY);

        //Create OnClick Listener For Button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editSearch.getText().toString().equals("")){
                    retrieveJson(editSearch.getText().toString(),country,API_KEY);
                }else
                {
                    retrieveJson("",country,API_KEY);
                }

            }
        });


    }

    public void retrieveJson(String query, String country, String apiKey){

        Call<Headlines> call;

        if(!editSearch.getText().toString().equals("")){
            call= ApiClient.getInstance().getApi().getSearchData(query,apiKey);
        }
        else
        {
            call= ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        }
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles() !=null){
                    articles.clear();
                    articles= response.body().getArticles();
                    adapter =new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    public String getCountry(){
        Locale locale=Locale.getDefault();
        String country= locale.getCountry();
        return country.toLowerCase();
    }




}