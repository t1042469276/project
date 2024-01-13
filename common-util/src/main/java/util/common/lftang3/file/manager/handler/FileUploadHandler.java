package util.common.lftang3.file.manager.handler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Callable;

/**
 * 每一块文件读写
 */
public class FileUploadHandler implements Callable<Integer> {

    private long startIndex;

    private long endIndex;

    //源文件路径
    private File sourceFile;

    //目标文件路径
    private String targetDirPath;


    public FileUploadHandler(long startIndex, long endIndex, File sourceFile, String targetDirPath) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.sourceFile = sourceFile;
        this.targetDirPath = targetDirPath;
    }

    @Override
    public Integer call() throws Exception {
        return copyFile();
    }

    private int copyFile() throws IOException {
        if (endIndex <= startIndex) {
            return 0;
        }
        File newFile = new File(targetDirPath);
        if (newFile.exists()) {
            return 1;
        }
        RandomAccessFile in = new RandomAccessFile(sourceFile, "rw");
        RandomAccessFile out = new RandomAccessFile(newFile, "rw");
        byte[] chars = new byte[1024 * 1024];
        in.seek(startIndex);
        out.seek(startIndex);
        while (endIndex >= startIndex) {
            int read = in.read(chars);
            if (read <= 0) {
                break;
            }
            out.write(chars, 0, read);
            startIndex += read;
        }
        out.close();
        in.close();
        return 1;
    }

}
