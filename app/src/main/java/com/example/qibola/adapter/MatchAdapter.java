package com.example.qibola.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.qibola.R;
import com.example.qibola.model.Match;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private List<Match> list;

    public MatchAdapter(List<Match> list) { this.list = list; }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int v) {
        View view = LayoutInflater.from(p.getContext()).inflate(R.layout.item_match, p, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Match m = list.get(pos);
        h.homeName.setText(m.match_hometeam_name);
        h.awayName.setText(m.match_awayteam_name);
        h.date.setText(m.match_date + " | " + m.match_time);

        Glide.with(h.itemView.getContext()).load(m.team_home_badge).into(h.homeLogo);
        Glide.with(h.itemView.getContext()).load(m.team_away_badge).into(h.awayLogo);
    }

    @Override public int getItemCount() { return list == null ? 0 : list.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView homeName, awayName, date;
        ImageView homeLogo, awayLogo;
        ViewHolder(View v) {
            super(v);
            homeName = v.findViewById(R.id.tvHomeName);
            awayName = v.findViewById(R.id.tvAwayName);
            date = v.findViewById(R.id.tvMatchDate);
            homeLogo = v.findViewById(R.id.imgHomeLogo);
            awayLogo = v.findViewById(R.id.imgAwayLogo);
        }
    }
}