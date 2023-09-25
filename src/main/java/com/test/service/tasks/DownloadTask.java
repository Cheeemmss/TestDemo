package com.test.service.tasks;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class DownloadTask extends Thread {
    private String url;
    private String filePath;
    private int startByte;
    private int endByte;
    private CountDownLatch countDownLatch;

    public DownloadTask(String url, String filePath, int startByte, int endByte, CountDownLatch countDownLatch) {
        this.url = url;
        this.filePath = filePath;
        this.startByte = startByte;
        this.endByte = endByte;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            URL fileUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);

            try (InputStream inputStream = connection.getInputStream();
                 OutputStream outputStream = new FileOutputStream(filePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}
