package util.common.lftang3.file.manager.handler;

import util.common.lftang3.algorithm.SegmentCollectionUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

public class FilesUploadHandler extends RecursiveTask<Integer> {

    private static Logger logger = Logger.getLogger("FilesUploadHandler");

    private int limit;

    private int rate;

    /**
     * 原文件夹
     */
    private List<File> files;
    /**
     * 目标文件夹
     */
    private String targetDir;


    protected FilesUploadHandler(int limit, int rate, List<File> files, String targetDir) {
        this.limit = limit;
        this.rate = rate;
        this.files = files;
        this.targetDir = targetDir;
    }

    @Override
    protected Integer compute() {
        // 一个任务处理多少文件
        if (files.size() <= limit) {
            logger.info("单任务文件数量:" + files.size());
            try {
                int sum = 0;
                for (File file : files) {
                    sum += copyFiles(file);
                }
                return sum;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        List<List<File>> lists = SegmentCollectionUtil.segmentList(files, files.size() / 2 + 1);
        FilesUploadHandler min = new FilesUploadHandler(limit, rate, lists.get(0), targetDir);
        FilesUploadHandler max = new FilesUploadHandler(limit, rate, lists.get(1), targetDir);
        min.fork();
        max.fork();
        return min.join() + max.join();
    }

    private int copyFiles(File file) throws IOException {
        File folder = new File(targetDir);
        if (folder.isFile()) {
            return 0;
        }
        if (!folder.exists()) {
            folder.mkdir();
        }
        File targetFile = new File(targetDir + "\\" + file.getName());
        if (targetFile.exists()) {
            return 1;
        }
        RandomAccessFile in = new RandomAccessFile(file, "rw");
        RandomAccessFile out = new RandomAccessFile(targetFile, "rw");
        byte[] bytes = new byte[rate];
        int len;
        while ((len = in.read(bytes)) > 0) {
            out.write(bytes, 0, len);
        }
        out.close();
        in.close();
        return 1;
    }

}
