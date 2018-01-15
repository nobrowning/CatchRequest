package com.dcg.processor;

import com.dcg.entity.CatchedRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duchenguang on 2018/1/15.
 */

public class YiKeXueListPageProcessor implements SubPageProcessor {
    public MatchOther processPage(Page page) {
        page.setCharset("utf-8");
        String html = page.getHtml().toString();
        Document document = Jsoup.parse(html);

        Elements requests = document.select("#content_list > li:not(li.col-lg-12.col-md-12.col-sm-12.col-xs-12.transparent.text-center)");
        if (requests.isEmpty())
            page.setSkip(true);
        List<CatchedRequest> catchedRequestList = new ArrayList<CatchedRequest>();
        List<String> reCheckUrl = new ArrayList<String>();
        String domain = document.select("body > div.container.detail_container.clear > div.col-lg-9.col-md-9.col-sm-8.col-xs-12.need_right.text-center > div > div.fl.selected > span").text();
        domain = domain.substring(domain.indexOf('：')+1,domain.length()-2);
        for (Element e:
                requests) {
            Elements content = e.select("a");
            String url = "http://task.yikexue.com"+content.attr("href");
            String detail = content.select("p:nth-child(5)").text().trim();
            String title = content.select("h5 > span.title").text().trim();
            if (detail.endsWith("...") || title.endsWith("...")){
                try {
                    String encodeDomain = URLEncoder.encode(domain,"utf-8");
                    reCheckUrl.add(url+"&&"+encodeDomain);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }else{
                String budget = content.select("p:nth-child(4)").text().substring(4);
                boolean finished = false;
                String status = content.select("p:nth-child(2)").text();
                if (status.equals("状态：已采纳方案 "))
                    finished = true;
                CatchedRequest catchedRequest = new CatchedRequest(title,detail,url,domain,budget,finished);
                catchedRequestList.add(catchedRequest);
            }
        }

        Elements nextPage = document.select("#page > div > div > a.nxt");
        if (nextPage != null){
            page.addTargetRequest("http://task.yikexue.com"+nextPage.attr("href"));
        }

        if (!catchedRequestList.isEmpty())
            page.putField("catchRequests",catchedRequestList);
        if (!reCheckUrl.isEmpty())
            page.addTargetRequests(reCheckUrl,1);

        return MatchOther.NO;
    }

    public boolean match(Request request) {
        return request.getUrl().startsWith("http://task.yikexue.com/demand/lists?");
    }
}
