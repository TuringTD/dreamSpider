package pds.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by peng on 2017/5/19.
 */
public class BlogSpider {


    static String SendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {

        // 定义即将访问的链接
        String url = "http://www.cnblogs.com/Amos-Turing/default.html?page=1";
        // 访问链接并获取页面内容
        String content = BlogSpider.SendGet(url);
        // 获取编辑推荐
        // 打印结果
        System.out.println(content);

    }




}
