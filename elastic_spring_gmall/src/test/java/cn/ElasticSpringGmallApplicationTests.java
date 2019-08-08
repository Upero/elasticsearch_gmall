package cn;

import cn.elasticsearch.SearchInfo.SearchInformation;
import cn.elasticsearch.service.ElasticsearchService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSpringGmallApplicationTests {

    @Autowired
    ElasticsearchService service;

    @Test
    public void contextLoads() {

        GetResponse getResponse = service.FindbygoodsId(1);

    }

    @Test
    public void SearchQuery() {
        SearchResponse searchResponse = service.SearchGoodsInTypeAndName("牙膏");
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();

        for (SearchHit hit : hits1) {
            System.out.println(hit.getSourceAsMap());
        }

    }

    @Test
    public void SearchMatchAll() {
        SearchResponse searchResponse = service.QueryMatchALL();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public void RangeSortFilter() {
        SearchResponse searchResponse = service.PriceRangeFilter(1, 20);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public void testMultiFunctionSearch() {
        SearchInformation searchInformation = new SearchInformation();
        searchInformation.setAsc(false);
        searchInformation.setKey(null);
        searchInformation.setIndex("goods");
        searchInformation.setLow(0);
        searchInformation.setHight(10000);
        SearchResponse searchResponse = service.MultiFunctionSearch(searchInformation);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Autowired
    private SearchInformation searchInformation;

    @Test
    public void TestSearchInfomation() {
        System.out.println(searchInformation);
    }

}

