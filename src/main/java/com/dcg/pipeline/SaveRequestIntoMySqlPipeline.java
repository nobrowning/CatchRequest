package com.dcg.pipeline;

import com.dcg.entity.CatchedRequest;
import com.dcg.repository.CatchedRequestRepo;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duchenguang on 2018/1/15.
 */

@Component
public class SaveRequestIntoMySqlPipeline implements Pipeline {

    @Resource
    CatchedRequestRepo catchedRequestRepo;

    public void process(ResultItems resultItems, Task task) {
        List<CatchedRequest> catchedRequestList = resultItems.get("catchRequests");
        if (catchedRequestList != null)
            catchedRequestRepo.save(catchedRequestList);

    }
}
