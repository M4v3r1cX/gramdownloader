package com.bsodsoftware.gramdownloader.services;

import android.content.Context;
import android.widget.Toast;

import com.bsodsoftware.gramdownloader.to.PageContentTO;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class GramDownloaderService {

    private Context _context;
    VideoDownloadService videoDownloadService;

    public GramDownloaderService(Context context) {
        this._context = context;
        this.videoDownloadService = new VideoDownloadService(_context);
    }

    public void process(String instaUrl) throws Exception {
        String html = new HTMLService().execute(instaUrl).get();
        if (html != null) {
            String searchPhrase = "{\"PostPage\":[";
            int startIndex = html.lastIndexOf(searchPhrase);
            int mainIndex = startIndex + searchPhrase.length();
            String searchPhrase2 = "{\"edges\":[]}}}}";
            int lastIndex = html.lastIndexOf(searchPhrase2);
            int finalIndex = lastIndex + searchPhrase2.length();
            String json = html.substring(mainIndex, finalIndex);                    // Esto debiera dejarnos con un json completo
            Gson gson = new Gson();
            PageContentTO to = gson.fromJson(json, PageContentTO.class);
            String urlVideo = to.getGraphql().getShortcode_media().getVideo_url();  // Porque faltaban Strings en esta wea
            queueDownload(urlVideo, to.getGraphql().getShortcode_media().getId() + ".mp4");
        }
    }

    private void queueDownload(String url, String videoName) throws ExecutionException, InterruptedException {
        boolean downloaded = videoDownloadService.execute(url, videoName).get();
        String message = "";
        if (downloaded) {
            message = "Video successfuly queued for download. Check your notifications!";
        } else {
            message = "Error downloading video.";
        }

        Toast.makeText(_context, message, Toast.LENGTH_LONG).show();
    }
}