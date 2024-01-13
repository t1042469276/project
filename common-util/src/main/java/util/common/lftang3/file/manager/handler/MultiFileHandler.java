package util.common.lftang3.file.manager.handler;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

public class MultiFileHandler implements Callable<Integer> {

    // 单个任务处理文件数量
    private int limit;

    // 读取文件速率
    private int rate;

    // 拆分后的文件集合
    private List<File> files;

    // 目标文件夹
    private String targetDir;

    public MultiFileHandler(int limit, int rate, List<File> files, String targetDir) {
        this.limit = limit;
        this.rate = rate;
        this.files = files;
        this.targetDir = targetDir;
    }

    @Override
    public Integer call() throws Exception {
        FilesUploadHandler filesUploadHandler = new FilesUploadHandler(limit, rate, files, targetDir);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.submit(filesUploadHandler).get();
    }

}
