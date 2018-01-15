package com.dcg.processor;

import com.dcg.entity.CatchedRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duchenguang on 2018/1/15.
 */

public class NtbListPageProcessor implements SubPageProcessor {


    public MatchOther processPage(Page page) {
        page.setCharset("utf-8");
        String html = page.getHtml().toString();
        Document document = Jsoup.parse(html);

        Elements requests = document.select("#datacontainer > li");
        List<CatchedRequest> catchedRequestList = new ArrayList<CatchedRequest>();
        List<String> reCheckUrl = new ArrayList<String>();
        for (Element e:
             requests) {
            Elements content = e.select("a");
            String url = "http://ntb.1633.com"+content.attr("href");
            String detail = content.select("span.hur1").text().trim();
            String title = content.select("span.h_title").text().trim();
            if (detail.endsWith("...") || title.endsWith("...")){
                reCheckUrl.add(url);
            }else{
                Elements otherInfo = content.select("span.hur2");
                String budget = otherInfo.select("span:nth-child(1)").text().substring(3);
                String domain = otherInfo.select("span:nth-child(2)").text().substring(5);
                boolean finished = false;
                String status = e.attr("class");
                if (status.equals("d11_jsnt d11_overb"))
                    finished = true;
                CatchedRequest catchedRequest = new CatchedRequest(title,detail,url,domain,budget,finished);
                catchedRequestList.add(catchedRequest);
            }
        }
        int currentPageNum = Integer.parseInt(document.select("body > div.d11_ntblb > div > div.page > span.thispage").text().trim());
        if (currentPageNum == 1){
            int lastPageNum = Integer.parseInt(document.select("body > div.d11_ntblb > div > div.page > a:nth-last-child(2)").text());
            for (int i = 2;i <= lastPageNum;i++){
                String addUrl = "http://ntb.1633.com/invest/list/?page="+i;
                page.addTargetRequest(addUrl);
                System.out.println(addUrl);
            }
        }

        if (!catchedRequestList.isEmpty())
            page.putField("catchRequests",catchedRequestList);
        if (!reCheckUrl.isEmpty())
            page.addTargetRequests(reCheckUrl,1);
        return MatchOther.NO;
    }

    public boolean match(Request request) {
        return request.getUrl().startsWith("http://ntb.1633.com/invest/list/?page=");
    }
}
