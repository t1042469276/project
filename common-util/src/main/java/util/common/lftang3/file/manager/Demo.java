package util.common.lftang3.file.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Demo {

    public static void main(String[] args) throws IOException {
        String sourceDir = "D:\\share\\test"; // 要被压缩的源文件目录路径
        String zipFileName = "D:\\share\\destination.zip"; // 目标压缩文件名及路径
        int numThreads = 4; // 使用的线程数

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            compressDir(sourceDir, sourceDir, zos, executor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void compressDir(String dirPath, String baseDir, ZipOutputStream zos, ExecutorService executor) throws Exception {
        File dir = new File(dirPath);
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                compressDir(file.getAbsolutePath(), baseDir, zos, executor);
            } else {
                String entryName = file.getAbsolutePath().substring(baseDir.length() + 1); // 相对路径
                Callable<Integer> task = () -> {
                    try {
                        zos.putNextEntry(new ZipEntry(entryName));
                        try (FileInputStream fis = new FileInputStream(file)) {
                            byte[] buffer = new byte[10240];
                            int length;
                            while ((length = fis.read(buffer)) > 0) {
                                zos.write(buffer, 0, length);
                            }
                        }
                        zos.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    return 1;
                };
                tasks.add(task);
            }
        }
        List<Future<Integer>> futures = executor.invokeAll(tasks);
        boolean flag = false;
        for (Future<Integer> future : futures) {
            Integer result = future.get();
            flag = false;
            if (result.intValue() == 1) {
                flag = true;
            }
        }
        if (flag) {
            executor.shutdown();
        }
    }
}
