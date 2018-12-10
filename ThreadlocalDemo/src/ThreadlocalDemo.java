import com.sun.corba.se.impl.orb.ParserTable;

/**
 * Created with IntelliJ IDEA.
 * Description:  验证threadlocal在每个线程中创建变量副本的效果
 * User:  张娅迪
 * Date: 2018/11/18
 * Time: 下午 4:46
 * Detail requirement: 为理解threadlocal写的demo
 * Method:
 */
public class ThreadlocalDemo {
    ThreadLocal<Long> longlocal =new ThreadLocal<Long>();
    ThreadLocal<String> stringlocal =new ThreadLocal<String>();

    public void set(){
        longlocal.set(Thread.currentThread().getId());
        stringlocal.set(Thread.currentThread().getName());
    }

    public Long getLong(){
        return longlocal.get();
    }

    public String getString(){
        return stringlocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadlocalDemo demo = new ThreadlocalDemo();
        demo.set();
        System.out.println("long=" + demo.getLong());
        System.out.println("String=" + demo.getString());

        Thread thread1 = new Thread(){
            public void run(){
                demo.set();
                System.out.println("long=" + demo.getLong());
                System.out.println("String=" + demo.getString());
            };
        };

        thread1.start();
        thread1.join();

        System.out.println("long=" + demo.getLong());
        System.out.println("String=" + demo.getString());
    }
}