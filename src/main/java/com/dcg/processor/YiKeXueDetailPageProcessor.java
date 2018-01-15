package com.dcg.processor;

import com.dcg.entity.CatchedRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duchenguang on 2018/1/15.
 */


public class YiKeXueDetailPageProcessor implements SubPageProcessor {



    public MatchOther processPage(Page page) {
        page.setCharset("utf-8");
        String html = page.getHtml().toString();
        Document document = Jsoup.parse(html);

        Elements content = document.select("body > div.container.need_detail_container > div.col-lg-9.col-md-8.col-sm-8.col-xs-12 > div.col-lg-12.col-md-12.col-sm-12.col-xs-12.detail_container");
        String title = content.select("h4").text().replace(content.select("h4 > span").text(),"");
        String detail = content.select("div:nth-child(2)").text();
        String budget = content.select("div.col-lg-12.col-md-12.col-sm-12.col-xs-12.need_pay").text().substring(5);
        String url = page.getUrl().regex("(.+)&&.+$").toString();
        boolean finished = false;
        String status = content.select("div.col-lg-12.col-md-12.col-sm-12.col-xs-12.close_time").text().trim();
        if (status.endsWith("（已停止收取方案）"))
            finished = true;

        String domain = page.getUrl().regex("&&(.+)$").toString();
        try {
            domain = URLDecoder.decode(domain,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CatchedRequest catchedRequest = new CatchedRequest(title,detail,url,domain,budget,finished);
        List<CatchedRequest> catchedRequestList = new ArrayList<CatchedRequest>();
        catchedRequestList.add(catchedRequest);
        page.putField("catchRequests",catchedRequestList);
        return MatchOther.NO;
    }

    public boolean match(Request request) {
        return request.getUrl().startsWith("http://task.yikexue.com/demand/detail?pid=");
    }
}
