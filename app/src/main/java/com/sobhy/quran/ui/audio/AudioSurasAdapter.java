package com.sobhy.quran.ui.audio;

import static com.sobhy.quran.ui.audio.RecitersFragment.isNetworkAvailable;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.AudioSura;

import java.io.File;
import java.util.ArrayList;

public class AudioSurasAdapter extends RecyclerView.Adapter<AudioSurasAdapter.SurasViewHolder> {
    ArrayList<AudioSura> SoraArrayList = new ArrayList<>();
    String folderName;

    BroadcastReceiver downloadCompleteReceiver;

    @NonNull
    @Override
    public SurasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_audio_suras, parent, false);
        return new SurasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SurasViewHolder holder, int position) {
        AudioSura Sora = SoraArrayList.get(position);
        holder.suraNum.setText(String.valueOf(Sora.getSuraNumber()));
        holder.suraName.setText(Sora.getSuraName());
        boolean isDownloaded = isSoundDownloaded(holder.itemView.getContext(), Sora);


        // Check if the sound is already downloaded
        if (isDownloaded) {
            holder.progressBar.setVisibility(View.GONE);
            holder.downloadImageView.setVisibility(View.GONE);
        }
        // check if the sound is downloading
        else if (Sora.isDownloading()) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.downloadImageView.setVisibility(View.GONE);
        } else {
            holder.downloadImageView.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);
            if (isNetworkAvailable(holder.itemView.getContext())) {
                holder.downloadImageView.setOnClickListener(v -> {
                    // Hide the download button and show the progress bar
                    holder.downloadImageView.setVisibility(View.GONE);
                    holder.progressBar.setVisibility(View.VISIBLE);

                    folderName = Sora.getReciterName() + "/" + Sora.getMoshafName();
                    File folder = new File(v.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), folderName);

                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdirs();
                    }
                    if (success) {
                        // Folder created successfully
                        // Start the download
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Sora.getSuraUrl()));
                        request.setDescription("Downloading");
                        request.setMimeType("audio/MP3");
                        request.setTitle(Sora.getSuraName());
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalFilesDir(v.getContext(), Environment.DIRECTORY_DOWNLOADS, folderName + "/" + Sora.getSuraName() + ".mp3");
                        DownloadManager manager = (DownloadManager) holder.itemView.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        long managerId = manager.enqueue(request);
                        Sora.setDownloading(true);

                        downloadCompleteReceiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                                if (id == managerId) {

                                    holder.progressBar.setVisibility(View.GONE);
                                    holder.downloadImageView.setVisibility(View.GONE);

                                }
                            }
                        };
                        holder.itemView.getContext().registerReceiver(downloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                    } else {
                        // Failed to create folder
                        Toast.makeText(v.getContext(), R.string.canot_download_file, Toast.LENGTH_SHORT).show();
                        holder.downloadImageView.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (isNetworkAvailable(v.getContext())) {
                Intent intent = new Intent(holder.itemView.getContext(), PlayerActivity.class);
                intent.putExtra("suraList", SoraArrayList);
                intent.putExtra("position", holder.getAdapterPosition());
                holder.itemView.getContext().startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return SoraArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<AudioSura> SoraArrayList) {
        this.SoraArrayList = SoraArrayList;
        notifyDataSetChanged();
    }

    static class SurasViewHolder extends RecyclerView.ViewHolder {
        TextView suraNum, suraName;
        ImageView downloadImageView;

        ProgressBar progressBar;

        public SurasViewHolder(@NonNull View itemView) {
            super(itemView);
            suraNum = itemView.findViewById(R.id.item_audio_suras_number);
            suraName = itemView.findViewById(R.id.item_audio_suras_name);
            downloadImageView = itemView.findViewById(R.id.item_audio_suras_download);
            progressBar = itemView.findViewById(R.id.item_audio_suras_progress);
        }
    }

    static boolean isSoundDownloaded(Context context, AudioSura Sora) {
        String folderName = Sora.getReciterName() + "/" + Sora.getMoshafName();
        File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), folderName);
        File soundFile = new File(folder, Sora.getSuraName() + ".mp3");
        Sora.setDownloading(false);
        return soundFile.exists();
    }

}