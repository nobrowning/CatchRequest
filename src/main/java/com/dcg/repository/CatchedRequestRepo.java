package com.dcg.repository;

import com.dcg.entity.CatchedRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duchenguang on 2018/1/15.
 */

@Repository("CatchedRequestRepo")
public interface CatchedRequestRepo extends JpaRepository<CatchedRequest,Long> {

}
