
package com.greendreamlimited.newsviewsv2.Models.NewsSource;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsSource {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private long totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NewsSource() {
    }

    /**
     * 
     * @param articles
     * @param totalResults
     * @param status
     */
    public NewsSource(String status, long totalResults, List<Article> articles) {
        super();
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
