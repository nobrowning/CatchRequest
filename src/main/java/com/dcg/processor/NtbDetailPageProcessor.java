package com.dcg.processor;

import com.dcg.entity.CatchedRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duchenguang on 2018/1/15.
 */

public class NtbDetailPageProcessor implements SubPageProcessor {
    public MatchOther processPage(Page page) {
        page.setCharset("utf-8");
        String html = page.getHtml().toString();
        Document document = Jsoup.parse(html);

        Elements content = document.select("body > div.d11_ntbdtl > div > div > div:nth-child(1)");

        String title = content.select("h3").text();
        String detail = content.select("p:nth-child(9) > span").text();
        String budget = content.select("p.d11_nxqp1 > span.sp1 > em").text();
        String domain = content.select("p:nth-child(7) > span").text();
        String url = page.getUrl().toString();
        boolean finished = true;
        String status = content.select("span.f_r.wjj").text();
        if (status.equals("需求未解决"))
            finished = false;
        CatchedRequest catchedRequest = new CatchedRequest(title,detail,url,domain,budget,finished);
        List<CatchedRequest> catchedRequestList = new ArrayList<CatchedRequest>();
        catchedRequestList.add(catchedRequest);
        page.putField("catchRequests",catchedRequestList);
        return MatchOther.NO;
    }

    public boolean match(Request request) {
        String url = request.getUrl();
        return url.startsWith("http://ntb.1633.com/invest/") && !url.contains("list");
    }
}
