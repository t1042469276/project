package util.common.lftang3.file.manager.bean;

import java.io.File;

public class FileConfig {

    /**
     * 文件块大小
     */
    private long fileBlockSize;

    private String sourceFilePath;

    private String targetDirPath;

    private File sourceFile;

    public File getSourceFile() {
        sourceFile = new File(sourceFilePath);
        return sourceFile;
    }

    public long getFileBlockSize() {
        return fileBlockSize;
    }

    public void setFileBlockSize(long fileBlockSize) {
        this.fileBlockSize = fileBlockSize;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public String getTargetDirPath() {
        return targetDirPath;
    }

    public void setTargetDirPath(String targetDirPath) {
        this.targetDirPath = targetDirPath;
    }
}
