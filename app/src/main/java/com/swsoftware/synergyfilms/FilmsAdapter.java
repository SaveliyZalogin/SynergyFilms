package com.swsoftware.synergyfilms;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder> {

    private final JSONArray films;
    private final Context context;

    public FilmsAdapter(Context context, JSONArray films) {
        this.context = context;
        this.films = films;
    }
    public static class FilmsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView year;
        TextView awards;
        TextView nominations;
        ImageButton copyButton;

        public FilmsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            year = itemView.findViewById(R.id.year);
            awards = itemView.findViewById(R.id.award);
            nominations = itemView.findViewById(R.id.nomination);
            copyButton = itemView.findViewById(R.id.copyButton);
        }
    }

    @NonNull
    @Override
    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_layout, parent, false);
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {
        try {
            JSONObject film = films.getJSONObject(position);
            String filmTitle = film.get("Film").toString();
            holder.title.setText(filmTitle);
            holder.year.setText(film.get("Year").toString());
            holder.awards.setText(film.get("Award").toString());
            holder.nominations.setText(film.get("Nomination").toString());
            holder.copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(new ClipData(
                            new ClipDescription("Film name", new String[]{ }),
                            new ClipData.Item(filmTitle)
                    ));
                    Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return films.length();
    }
}
