package com.example.qibola.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.qibola.R;
import com.example.qibola.model.Standing;
import java.util.List;

public class StandingAdapter extends RecyclerView.Adapter<StandingAdapter.ViewHolder> {
    private List<Standing> list;

    public StandingAdapter(List<Standing> list) {
        this.list = list;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_standing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Standing s = list.get(position);

        holder.rank.setText(s.overall_league_position);
        holder.name.setText(s.team_name);
        holder.pld.setText("P: " + s.overall_league_payed);
        holder.pts.setText(s.overall_league_PTS + " Pts");

        // --- LOGIKA LOGO TIM ---
        Glide.with(holder.itemView.getContext())
                .load(s.team_badge)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(
                        // Cadangan jika link API 404
                        Glide.with(holder.itemView.getContext())
                                .load("https://ssl.gstatic.com/onebox/media/sports/logos/" + s.team_name.toLowerCase().replace(" ", "_") + "_96x96.png")
                )
                .into(holder.logo);

        // --- LOGIKA WARNA (Hanya Real Madrid yang Biru) ---
        String teamNameLower = s.team_name.toLowerCase();

        if (teamNameLower.contains("real madrid")) {
            // Warna Biru khusus Real Madrid
            holder.itemView.setBackgroundColor(0xFF1D4ED8);
        } else {
            // Warna Biru Gelap standar untuk Barcelona dan tim lainnya
            holder.itemView.setBackgroundColor(0xFF1E293B);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank, name, pld, pts;
        ImageView logo;
        ViewHolder(View v) {
            super(v);
            rank = v.findViewById(R.id.tvRank);
            name = v.findViewById(R.id.tvTeamName);
            pld = v.findViewById(R.id.tvPlayed);
            pts = v.findViewById(R.id.tvPoints);
            logo = v.findViewById(R.id.imgLogo);
        }
    }
}