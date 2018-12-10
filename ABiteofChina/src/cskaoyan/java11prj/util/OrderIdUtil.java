package cskaoyan.java11prj.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/18
 * Time: 下午 9:20
 * Detail requirement:
 * Method:
 */
public class OrderIdUtil {
    public static int generateOrderId(){
        int oid = -1;
        while (oid < 0){
            oid = UUID.randomUUID().hashCode();
        }
        return oid;
    }

    @Test
    public void testUUIDOid(){
        int oid = OrderIdUtil.generateOrderId();

        System.out.println("oid=" + oid);
        Assert.assertNotNull(oid);
    }
}

/*
public class test{
    @Test
    public void testUUIDOid(){
        int oid = OrderIdUtil.generateOrderId();

        System.out.println("oid=" + oid);
        Assert.assertNotNull(oid);
    }
}*/
