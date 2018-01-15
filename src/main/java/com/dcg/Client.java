package com.dcg;

import com.dcg.crawler.NtbCrawler;
import com.dcg.crawler.YiKeXueCrawler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by duchenguang on 2018/1/15.
 */

public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-config.xml");
        NtbCrawler ntbCrawler = (NtbCrawler) ac.getBean("NtbCrawler");
        ntbCrawler.run();
        YiKeXueCrawler yiKeXueCrawler = (YiKeXueCrawler)ac.getBean("YiKeXueCrawler");
        yiKeXueCrawler.run();
    }
}
