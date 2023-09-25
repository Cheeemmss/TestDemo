package com.test.controller;

/**
 * @Author cheems
 * @Date 2023/9/23 3:33
 */
import com.test.service.tasks.DownloadTask;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@WebServlet("/fileService/down.do")
public class FileDownloadServlet extends HttpServlet {
    private static final int CHUNK_SIZE = 512 * 128; // 切片大小
    private static final int NUM_THREADS = 4;

    private ExecutorService executorService;

    @Override
    public void init() throws ServletException {
        super.init();
        executorService = Executors.newFixedThreadPool(NUM_THREADS);
    }

    @Override
    public void destroy() {
        super.destroy();
        executorService.shutdown();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        String fileName = extractFileName(url);

        // 创建临时目录存储下载的切片
        String tempDirPath = getServletContext().getRealPath("/temp");
        File tempDir = new File(tempDirPath);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }


        try {
            URL fileUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setRequestMethod("HEAD");
            int fileSize = connection.getContentLength();

            // 计算切片数量
            int numChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);

            // 切片下载
            CountDownLatch countDownLatch = new CountDownLatch(numChunks);
            for (int i = 0; i < numChunks; i++) {
                int startByte = i * CHUNK_SIZE;
                int endByte = Math.min((i + 1) * CHUNK_SIZE - 1, fileSize - 1);
                String tempFilePath = tempDirPath + File.separator + fileName + ".part" + i;
                Runnable downloadTask = new DownloadTask(url, tempFilePath, startByte, endByte,countDownLatch);
                executorService.execute(downloadTask);
            }

            countDownLatch.await();

            // 合并切片
            String filePath = mergeChunks(tempDirPath, fileName, numChunks);

            // 生成可下载的URL
            String downloadUrl = generateDownloadUrl(request, filePath);

            response.setContentType("text/plain");
            response.getWriter().write(downloadUrl);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String extractFileName(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
            return url.substring(lastSlashIndex + 1);
        }
        return "downloaded_file";
    }

    private String mergeChunks(String tempDirPath, String fileName, int numChunks) throws IOException {
        File downloadFolder = new File(getServletContext().getRealPath("/downloads"));
        if(!downloadFolder.exists()) {
            downloadFolder.mkdirs();
        }
        String outputPath = getServletContext().getRealPath("/downloads") + File.separator + fileName;
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            for (int i = 0; i < numChunks; i++) {
                String partFilePath = tempDirPath + File.separator + fileName + ".part" + i;
                try (FileInputStream fis = new FileInputStream(partFilePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
                // 删除临时切片文件
                new File(partFilePath).delete();
            }
        }
        return outputPath;
    }


    private String generateDownloadUrl(HttpServletRequest request, String filePath) {
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        return "http://" + serverName + ":" + serverPort + contextPath + "/downloads/" + new File(filePath).getName();
    }
}


