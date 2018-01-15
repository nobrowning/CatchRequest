package com.dcg.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by duchenguang on 2018/1/15.
 */

@Entity
@Table(name = "request")
public class CatchedRequest implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String detail;

    private String url;

    private String domain;

    private String budget;

    private Boolean finished;

    public CatchedRequest() {
    }


    public CatchedRequest(String title, String detail, String url, String domain, String budget, Boolean finished) {
        this.title = title;
        this.detail = detail;
        this.url = url;
        this.domain = domain;
        this.budget = budget;
        this.finished = finished;
    }



    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "CatchedRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", describe='" + detail + '\'' +
                ", url='" + url + '\'' +
                ", domain='" + domain + '\'' +
                ", budget='" + budget + '\'' +
                ", finished=" + finished +
                '}';
    }
}
