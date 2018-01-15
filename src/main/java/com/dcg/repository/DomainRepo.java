package com.dcg.repository;

import com.dcg.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duchenguang on 2018/1/15.
 */

@Repository
public interface DomainRepo extends JpaRepository<Domain,Long> {

    List<Domain> findByParentNotNull();
}
