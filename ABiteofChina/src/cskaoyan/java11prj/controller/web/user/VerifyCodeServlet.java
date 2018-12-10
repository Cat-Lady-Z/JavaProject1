package cskaoyan.java11prj.controller.web.user;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description: 验证码的随机动态生成以及显示
 * User:  张娅迪
 * Date: 2018/11/14
 * Time: 下午 5:20
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "VerifyCodeServlet",urlPatterns = "/verifyCode.jpg")
public class VerifyCodeServlet extends HttpServlet {
    //集合保存所有的成语
    private ArrayList<String> words = new ArrayList<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    public void init() throws ServletException {
        //在初始化阶段就把存储文件读进来
        //web工程文件，必须使用绝对磁盘路径
        String path = getServletContext().getRealPath("/WEB-INF/new_words.txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
            String line;
            while ((line = reader.readLine()) != null){
                words.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、在内存中绘制一张图片 -- 内存开辟绘制图片的空间
        int width = 120;
        int height = 30;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //2、图片绘制的参数设置： 背景颜色、边框
        //1）背景颜色
        Graphics graphics = bufferedImage.getGraphics();//得到画笔
        graphics.setColor(getRandColor(200,250));
        graphics.fillRect(0,0,width,height);

        //2)绘制边框
        graphics.setColor(Color.WHITE);
        graphics.drawRect(0,0,width-1,height-1);

        //3、生成4个随机数字
        Graphics2D graphics2D = (Graphics2D) graphics;
        // 设置输出字体
        graphics2D.setFont(new Font("宋体",Font.BOLD,18));

        //获得成语
        Random random = new Random();
        int index = random.nextInt(words.size());
        String word = words.get(index);
        System.out.println("word=" + word);

        //  定义x坐标
        int x = 10;
        for (int i = 0; i < word.length(); i++) {
            //随机颜色
            graphics2D.setColor(new Color(20 + random.nextInt(110),20+random.nextInt(110),
                    20+random.nextInt(110)));
            //旋转---30度
            int jiaodu = random.nextInt(60) - 30;
            double theda = jiaodu * Math.PI/180;

            char c = word.charAt(i);
            graphics2D.rotate(theda,x,20);
            graphics2D.drawString(String.valueOf(c),x,20);
            graphics2D.rotate(-theda,x,20);
            x += 30;
        }

        //把验证码内容保存在session中
        request.getSession().setAttribute("checkcode_session",word);

        //4、绘制干扰线
        graphics.setColor(getRandColor(160,200));
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i = 0; i < 30; i++) {
            x1 = random.nextInt(width);
            x2 = random.nextInt(12);
            y1 = random.nextInt(height);
            y2 = random.nextInt(12);
            graphics.drawLine(x1,y1,x1+x2,x2+y2);
        }


        //将上面的图片输出到浏览器
        graphics.dispose();  //释放资源
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());
    }

    private Color getRandColor(int fc, int bc) {
        //取随机的颜色
        Random random = new Random();

        if (fc > 255){
            fc = 255;
        }
        if (bc >255){
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }
}
