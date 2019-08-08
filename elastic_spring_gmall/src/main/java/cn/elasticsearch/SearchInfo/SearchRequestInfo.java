package cn.elasticsearch.SearchInfo;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchRequestInfo {

    @Autowired
    private SearchRequest searchRequest;

    private SearchInformation searchInformation;

    @Autowired
    private SearchSourceBuilder searchSourceBuilder;

    private Integer SIZE = 10;

    public void executeSearch() {
        initSearchQuery();
        MatchQuerybyKeyBulider();
        RangeBulider();
        SortBulider();
        CompleteSearchRequest();
    }

    public SearchRequest getSearchRequest() {
        return searchRequest;
    }

    private void initSearchQuery() {
        this.searchRequest.indices(this.searchInformation.getIndex());
        this.searchSourceBuilder.from(0);
        this.searchSourceBuilder.size(SIZE);
    }

    private void MatchQuerybyKeyBulider() {
        if (this.searchInformation.getKey() != null) {
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(this.searchInformation.getKey(), "name", "type");
            this.searchSourceBuilder.query(multiMatchQueryBuilder);
        }
    }

    private void RangeBulider() {
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("price")
                .from(this.searchInformation.getLow())
                .to(this.searchInformation.getHight());
        this.searchSourceBuilder.query(rangeQueryBuilder);
    }

    private void SortBulider() {
        FieldSortBuilder sort = SortBuilders.fieldSort("price");
        if (this.searchInformation.getAsc()) {
            sort.order(SortOrder.ASC);
        } else {
            sort.order(SortOrder.DESC);
        }
        this.searchSourceBuilder.sort(sort);
    }

    private void CompleteSearchRequest() {
        this.searchRequest.source(this.searchSourceBuilder);
    }

    public SearchInformation getSearchInformation() {
        return searchInformation;
    }

    public void setSearchInformation(SearchInformation searchInformation) {
        this.searchInformation = searchInformation;
        executeSearch();
    }
}
