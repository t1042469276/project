package util.common.lftang3.file.manager;

import util.common.lftang3.algorithm.SegmentCollectionUtil;
import util.common.lftang3.file.manager.bean.FileConfig;
import util.common.lftang3.file.manager.bean.MultiFileConfig;
import util.common.lftang3.file.manager.handler.FileUploadHandler;
import util.common.lftang3.file.manager.handler.MultiFileHandler;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class FileUpload {

    private static Logger logger = Logger.getLogger("FileUpload");

    public static Map<String, Integer> copyFile(FileConfig fileConfig, ExecutorService executorService) throws InterruptedException {
        long length = fileConfig.getSourceFile().length();
        //分成size
        long size = length / fileConfig.getFileBlockSize();
        if (length % fileConfig.getFileBlockSize() > 0) {
            size = size + 1;
        }
        List<FileUploadHandler> tasks = new ArrayList<>();
        for (long i = 0; i < size; i++) {
            long startIndex = fileConfig.getFileBlockSize() * i;
            long endIndex = fileConfig.getFileBlockSize() * (i + 1);
            FileUploadHandler task = new FileUploadHandler(startIndex, endIndex, fileConfig.getSourceFile(), fileConfig.getTargetDirPath());
            tasks.add(task);
        }
        List<Future<Integer>> futures = executorService.invokeAll(tasks);
        Map<String, Integer> map = new HashMap<>();
        int sum = 0;
        for (Future<Integer> future : futures) {
            try {
                Integer i = future.get();
                sum += i;
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        map.put("SUCCESS", sum);
        map.put("FAIL", futures.size() - sum);
        executorService.shutdown();
        System.out.println("复制完成!");
        return map;
    }

    public static void copyFiles(MultiFileConfig multiFileConfig, ExecutorService executorService) {
        if (multiFileConfig == null) {
            return;
        }
        if (multiFileConfig.getFileSum() == 0) {
            return;
        }
        // 一个文件夹中的文件数量
        File folder = new File(multiFileConfig.getSourceFolderPath());
        File[] listFiles = folder.listFiles();
        if (listFiles.length == 0) {
            return;
        }
        //分成n个文件夹
        int count = listFiles.length / multiFileConfig.getFileSum() + 1;
        List<List<File>> lists = SegmentCollectionUtil.segmentList(Arrays.asList(listFiles), multiFileConfig.getFileSum());
        Collection<Callable<Integer>> tasks = new ArrayList<>();
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                List<File> files = lists.get(i);
                MultiFileHandler task = new MultiFileHandler(multiFileConfig.getLimit(), multiFileConfig.getRate(), files, multiFileConfig.getTargetFolderPath() + "\\" + i);
                tasks.add(task);
            }
            List<Future<Integer>> futures = executorService.invokeAll(tasks);
            boolean flag = true;
            for (Future<Integer> future : futures) {
                Integer o = future.get();
                if (o == null) {
                    flag = false;
                }
                logger.info("已完成文件数量:" + o.intValue());
            }
            if (flag) {
                executorService.shutdown();
                logger.info("总共耗时:" + (System.currentTimeMillis() - start) + "毫秒");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
