package com.sinothk.image.selected;

/**
 * <pre>
 *  创建:  梁玉涛 2019/3/29 on 11:52
 *  项目:  WuMinAndroid
 *  描述:
 *  更新: 2020年7月6日 10:11:40
 * <pre>
 */
public class ImgSelectEntity {

    private String id;
    private String bizId;
    private String url;
    private String filePath;
    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
