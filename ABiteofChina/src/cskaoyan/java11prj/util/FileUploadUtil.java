package cskaoyan.java11prj.util;

import com.sun.javafx.collections.MappingChange;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 处理表单上传，文件上传
 * User:  张娅迪
 * Date: 2018/11/18
 * Time: 下午 9:03
 * Detail requirement:
 * Method:
 */
public class FileUploadUtil {
    private static ServletFileUpload servletFileUpload;
    static {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        servletFileUpload = new ServletFileUpload(factory);
    }

    public static byte[] handleFileUpload(HttpServletRequest request, Map<String,String> parametermap) throws FileUploadException, UnsupportedEncodingException {
        byte[] uploadFile = null;
        List<FileItem> items;
        items = servletFileUpload.parseRequest(request);
        for (FileItem item: items) {
            if (item.isFormField()){
                //简单表单项组件的数据处理，键值对
                parametermap.put(item.getFieldName(),item.getString("utf-8"));
            } else {
                parametermap.put(item.getFieldName(),item.getName());
                uploadFile = item.get();
            }
        }
        return uploadFile;
    }
}