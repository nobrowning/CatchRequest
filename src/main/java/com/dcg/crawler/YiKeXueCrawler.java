package com.dcg.crawler;

import com.dcg.entity.Domain;
import com.dcg.pipeline.SaveRequestIntoMySqlPipeline;
import com.dcg.processor.NtbDetailPageProcessor;
import com.dcg.processor.NtbListPageProcessor;
import com.dcg.processor.YiKeXueDetailPageProcessor;
import com.dcg.processor.YiKeXueListPageProcessor;
import com.dcg.repository.CatchedRequestRepo;
import com.dcg.repository.DomainRepo;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duchenguang on 2018/1/15.
 */

@Component("YiKeXueCrawler")
public class YiKeXueCrawler {

    @Resource
    SaveRequestIntoMySqlPipeline saveRequestIntoMySqlPipeline;

    @Resource
    DomainRepo domainRepo;


    public void run(){
        Site site = Site.me().setCycleRetryTimes(20).setSleepTime(500).setTimeOut(5000)
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/604.3.5 (KHTML, like Gecko) Version/11.0.1 Safari/604.3.5");
        CompositePageProcessor compositePageProcessor = new CompositePageProcessor(site);
        compositePageProcessor.addSubPageProcessor(new YiKeXueListPageProcessor());
        compositePageProcessor.addSubPageProcessor(new YiKeXueDetailPageProcessor());

        Spider spider = Spider.create(compositePageProcessor)
                .setScheduler(new PriorityScheduler())
                .addPipeline(saveRequestIntoMySqlPipeline)
                .addPipeline(new ConsolePipeline())
                .thread(5);
        List<Domain> domains = domainRepo.findByParentNotNull();
        for (Domain d:
             domains) {
            Long domainId = d.getId();
            String url = "http://task.yikexue.com/demand/lists?page=1&cid="+domainId;
            spider.addRequest(new Request(url));
        }
//        spider.addRequest(new Request("http://task.yikexue.com/demand/lists?page=1&cid=250"));
        spider.run();
    }
}
