package com.example.qibola.api;

import com.example.qibola.model.Standing;
import com.example.qibola.model.Match;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/")
    Call<List<Standing>> getStandings(
            @Query("action") String action,
            @Query("league_id") String leagueId,
            @Query("APIkey") String apiKey
    );

    @GET("/")
    Call<List<Match>> getMatches(
            @Query("action") String action,
            @Query("from") String from,
            @Query("to") String to,
            @Query("league_id") String leagueId,
            @Query("APIkey") String apiKey
    );
}