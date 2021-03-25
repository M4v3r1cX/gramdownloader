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
            int finalIndex = 0;
            int lastIndex = html.lastIndexOf(searchPhrase2);
            if (lastIndex != -1) {
                finalIndex = lastIndex + searchPhrase2.length();
            } else {
                // Patch to download videos from suggested or saved posts
                searchPhrase2 = "]},\"hostname\"";
                finalIndex = html.lastIndexOf(searchPhrase2);
            }
            String json = html.substring(mainIndex, finalIndex);                    // Esto debiera dejarnos con un json completo
            Gson gson = new Gson();
            PageContentTO to = gson.fromJson(json, PageContentTO.class);
            String urlVideo = to.getGraphql().getShortcode_media().getVideo_url();  // Porque faltaban Strings en esta wea
            String filename = "";
            if (urlVideo != null && !urlVideo.isEmpty()) {
                filename = to.getGraphql().getShortcode_media().getId() + ".mp4";
                queueDownload(urlVideo, filename);
            } else {
                if (to.getGraphql().getShortcode_media().getEdge_sidecar_to_children() != null) {
                    for (PageContentTO.Edges edge : to.getGraphql().getShortcode_media().getEdge_sidecar_to_children().getEdges()) {
                        if (edge.getNode().getVideo_url() != null && !edge.getNode().getVideo_url().isEmpty()) {
                            urlVideo = edge.getNode().getVideo_url();
                            filename = edge.getNode().getId() + ".mp4";
                            queueDownload(urlVideo,filename);
                        } else {
                            throw new Exception("Could not retrieve video URL. Sorry! Gotta work on it!");
                        }
                    }

                } else {
                    throw new Exception("Could not retrieve video URL. Sorry! Gotta work on it!");
                }
            }
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
