package com.dcg.crawler;

import com.dcg.pipeline.SaveRequestIntoMySqlPipeline;
import com.dcg.processor.NtbDetailPageProcessor;
import com.dcg.processor.NtbListPageProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

import javax.annotation.Resource;

/**
 * Created by duchenguang on 2018/1/15.
 */

@Component("NtbCrawler")
public class NtbCrawler {

    @Resource
    SaveRequestIntoMySqlPipeline saveRequestIntoMySqlPipeline;

    public void run(){
        Site site = Site.me().setCycleRetryTimes(20).setSleepTime(500).setTimeOut(5000)
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/604.3.5 (KHTML, like Gecko) Version/11.0.1 Safari/604.3.5");
        CompositePageProcessor compositePageProcessor = new CompositePageProcessor(site);
        compositePageProcessor.addSubPageProcessor(new NtbListPageProcessor());
        compositePageProcessor.addSubPageProcessor(new NtbDetailPageProcessor());

        Spider spider = Spider.create(compositePageProcessor)
                .setScheduler(new PriorityScheduler())
                .addPipeline(saveRequestIntoMySqlPipeline)
                .addPipeline(new ConsolePipeline())
                .addUrl("http://ntb.1633.com/invest/list/?page=1")
                .thread(5);
        spider.run();

    }
}
