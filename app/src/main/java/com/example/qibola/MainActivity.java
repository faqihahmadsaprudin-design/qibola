package com.example.qibola;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qibola.adapter.StandingAdapter;
import com.example.qibola.adapter.MatchAdapter;
import com.example.qibola.api.ApiService;
import com.example.qibola.model.Standing;
import com.example.qibola.model.Match;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ApiService api;
    private String selectedLeagueId = "152";
    private final String KEY = "9a1258497b6add2f19925e18be18560c491f819ea524cc7d48af2dab86502419";

    // URUTAN HARUS SAMA PERSIS ANTARA NAMA DAN ID
    String[] leagueNames = {"La Liga (Spanyol)", "Premier League (Inggris)", "Serie A (Italia)", "Bundesliga (Jerman)"};
    String[] leagueIds = {"302", "152", "207", "175"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rvContent);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Spinner spinner = findViewById(R.id.spinnerLeague);
        Button btnStandings = findViewById(R.id.btnStandings);
        Button btnMatches = findViewById(R.id.btnMatches);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apiv2.apifootball.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ApiService.class);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, leagueNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                selectedLeagueId = leagueIds[pos];
                rv.setAdapter(null); // Bersihkan layar saat ganti liga
                getKlasemen(); // Load otomatis saat pilih liga
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        btnStandings.setOnClickListener(v -> getKlasemen());
        btnMatches.setOnClickListener(v -> getJadwal());
    }

    private void getKlasemen() {
        api.getStandings("get_standings", selectedLeagueId, KEY).enqueue(new Callback<List<Standing>>() {
            @Override
            public void onResponse(Call<List<Standing>> call, Response<List<Standing>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Standing> data = response.body();
                    Log.d("API_SUCCESS", "Data masuk: " + data.size());
                    rv.setAdapter(new StandingAdapter(data));
                }
            }
            @Override
            public void onFailure(Call<List<Standing>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getJadwal() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String from = sdf.format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        String to = sdf.format(cal.getTime());

        api.getMatches("get_events", from, to, selectedLeagueId, KEY).enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rv.setAdapter(new MatchAdapter(response.body()));
                } else {
                    Toast.makeText(MainActivity.this, "Jadwal Kosong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<List<Match>> call, Throwable t) {}
        });
    }
}