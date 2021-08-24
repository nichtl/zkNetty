package com.example.zknetty.zkNetty.Beans;

/**
 * @Author Nicht
 * @description  文件上传请求信息
 * @Time 2021/4/12
 * @Link
 */
public class FileInfo {
private  String file_type;
private  String file_size;
private  String file_name_len;
private  String file_name;
private  String pos_info;//没有给空
private  String time;

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_name_len() {
        return file_name_len;
    }

    public void setFile_name_len(String file_name_len) {
        this.file_name_len = file_name_len;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getPos_info() {
        return pos_info;
    }

    public void setPos_info(String pos_info) {
        this.pos_info = pos_info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "file_type='" + file_type + '\'' +
                ", file_size='" + file_size + '\'' +
                ", file_name_len='" + file_name_len + '\'' +
                ", file_name='" + file_name + '\'' +
                ", pos_info='" + pos_info + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
