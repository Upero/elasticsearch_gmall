package cn.elasticsearch.service.Impl;

import cn.elasticsearch.SearchInfo.SearchInformation;
import cn.elasticsearch.SearchInfo.SearchRequestInfo;
import cn.elasticsearch.service.ElasticsearchService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    SearchRequestInfo searchRequestInfo;

    void FindById(Integer id) {

    }

    //创建索引
    public CreateIndexResponse createIndex() throws Exception {
        CreateIndexRequest request = new CreateIndexRequest("person");
        request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 1));
        CreateIndexResponse response = restHighLevelClient.indices().create(request);
        return response;
    }

    //searchQuery
    @Override
    public SearchResponse SearchGoodsInTypeAndName(String key) {
        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(key, "name", "type"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    //使用match查询
    @Override
    public SearchResponse QueryMatchALL() {
        SearchRequest request = new SearchRequest("goods");
        request.types("doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        request.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    //多功能search

    @Override
    public SearchResponse MultiFunctionSearch(SearchInformation searchInformation) {
        this.searchRequestInfo.setSearchInformation(searchInformation);
        SearchRequest searchRequest = this.searchRequestInfo.getSearchRequest();
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    //价格筛选
    public SearchResponse PriceRangeFilter(Integer low, Integer hight) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("goods");
        searchRequest.types("doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        //构造范围查询
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("price").from(low).to(hight);
        //添加进sourcebulider中
        searchSourceBuilder.query(rangeQueryBuilder);
        //构造排序查询
        FieldSortBuilder sort = SortBuilders.fieldSort("price");
        sort.order(SortOrder.ASC);
        //添加进sourcebulider中
        searchSourceBuilder.sort(sort);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    //查询
    public GetResponse FindbygoodsId(Integer id) {

        //引用_source字段检索，默认启用
        GetRequest getRequest = new GetRequest("goods", "doc", Integer.toString(id));
        //getRequest.fetchSourceContext(new FetchSourceContext(false));
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(getResponse);
        return getResponse;
    }

}
