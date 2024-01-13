package util.common.lftang3.file.manager.bean;

public class MultiFileConfig {

    // 文件夹路径
    private String sourceFolderPath;

    //目标文件夹路径
    private String targetFolderPath;

    // 一个文件夹中存放文件数量
    private int fileSum;

    private int limit;

    private int rate;

    public String getSourceFolderPath() {
        return sourceFolderPath;
    }

    public void setSourceFolderPath(String sourceFolderPath) {
        this.sourceFolderPath = sourceFolderPath;
    }

    public String getTargetFolderPath() {
        return targetFolderPath;
    }

    public void setTargetFolderPath(String targetFolderPath) {
        this.targetFolderPath = targetFolderPath;
    }

    public int getFileSum() {
        return fileSum;
    }

    public void setFileSum(int fileSum) {
        if (fileSum > 0) {
            this.fileSum = fileSum;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > 0) {
            this.limit = limit;
        }

    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        if (rate > 0) {
            this.rate = rate;
        }
    }
}
