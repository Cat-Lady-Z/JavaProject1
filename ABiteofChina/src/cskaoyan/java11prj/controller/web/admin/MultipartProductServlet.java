package cskaoyan.java11prj.controller.web.admin;

import cskaoyan.java11prj.domain.Product;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import cskaoyan.java11prj.service.impl.ProductServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/09
 * Time: 下午 4:49
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "MultipartProductServlet",urlPatterns = "/admin/MultipartProductServlet")
public class MultipartProductServlet extends HttpServlet {
    ProductServiceImpl productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        switch (op){
            case "addProduct":
                if (addProduct(request,response))
                    response.getWriter().println("添加成功.....");
                 else
                    response.getWriter().println("添加失败.....");
                response.setHeader("refresh","0;url="+ request.getContextPath() + "/admin/product/addProduct.jsp");
                break;
            case "updateProduct":
                if (updateProduct(request,response))
                    response.getWriter().println("修改成功.....");
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/admin/product/productList.jsp");
                break;
            default:
                System.out.println("操作失败");
                break;
        }
    }

    private boolean updateProduct(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        try {
            Product product = getProductFromMultiForm(request, response);
            if (product != null) {
                String pid = product.getPid();
                productService.deleteProductByPid(pid);
                Boolean isSuccess = productService.addProduct(product);
                if (isSuccess)
                    session.setAttribute("product",product);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    private boolean addProduct(HttpServletRequest request, HttpServletResponse response) {
        Product product = getProductFromMultiForm(request, response);
        if (product == null)
            return false;

        Boolean isSuccess = false;
        try {
            isSuccess = productService.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    private Product getProductFromMultiForm(HttpServletRequest request, HttpServletResponse response){
        //1、建工厂，建具备属性的生产线
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024*1024*5);
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File)servletContext.getAttribute("javax.servlet.context.tempdir");

        factory.setRepository(repository);

        //新建处理表单文件上传到upload
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024*1024*1024*5);
        upload.setHeaderEncoding("utf-8");

        List<FileItem> items = null;
        Product product = new Product();
        try {
            items = upload.parseRequest(request);
            Iterator<FileItem> iterator = items.iterator();

            while (iterator.hasNext()){
                FileItem item = iterator.next();

                if (item.isFormField()){
                    String fieldName = item.getFieldName();
                    if (item.getSize() == 0 || fieldName == null)
                        return null;

                    switch (fieldName) {
                        case "pid":
                            String pid = item.getString("utf-8");
                            if (pid != null && "".equals(pid))
                                product.setPid(pid);
                            break;
                        case "pname":
                            product.setPname(item.getString("utf-8"));
                            break;
                        case "estoreprice":
                            double estorepriceDouble = Double.parseDouble(item.getString("utf-8"));
                            if (estorepriceDouble>=0)
                                product.setEstoreprice(estorepriceDouble);
                            break;
                        case "markprice":
                            double markpriceDouble = Double.parseDouble(item.getString("utf-8"));
                            if (markpriceDouble>=0)
                                product.setMarkprice(markpriceDouble);
                            break;
                        case "pnum":
                            int pnum = Integer.parseInt(item.getString("utf-8"));
                            if (pnum >=0)
                                product.setPnum(pnum);
                            break;
                        case "cid":
                            int cid = Integer.parseInt(item.getString("utf-8"));
                            if (cid>0)
                                product.setCid(cid);
                            break;
                        case "desc":
                            product.setDesc(item.getString("utf-8"));
                            break;
                        default:
                            System.out.println("不是属性。");
                    }
                } else{
                    String imgurl = processFileField(item);
                    product.setImgurl(imgurl);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (NumberFormatException e) {
        e.printStackTrace();
    }

        return product;
    }

    private String processFileField(FileItem item) {
        String fileName = item.getName();
        if (item.getSize() == 0)
            return null;

        String uuid = UUID.randomUUID().toString();
        fileName = uuid + fileName;

        //4) 获取存储路径-

        String realPath = getServletContext().getRealPath("/myproduct/");

        //5）建8级目录
        //获取hashcode，一个数，32bit -> 分为4bit一个16进制的数
        int i = fileName.hashCode();
        String hashcode = Integer.toHexString(i);

        char[] chars = hashcode.toCharArray();
        /*String eightPath = "";
        for (char c:chars) {
            eightPath += "/" + c + "";
        }*/
        File folder = new File(realPath);
        if (!folder.exists()){
            folder.mkdirs();
        }

        //新建文件，把文件存进磁盘
        String path = realPath + "/" + fileName;
        File uploadFile = new File(path);

        try {
            item.write(uploadFile);
            return "myproduct" + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            item.delete();
        }
        return null;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
