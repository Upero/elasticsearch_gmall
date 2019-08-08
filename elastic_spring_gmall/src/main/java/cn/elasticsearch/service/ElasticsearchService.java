package cn.elasticsearch.service;

import cn.elasticsearch.SearchInfo.SearchInformation;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;

public interface ElasticsearchService {

    CreateIndexResponse createIndex() throws Exception;

    GetResponse FindbygoodsId(Integer id);

    SearchResponse SearchGoodsInTypeAndName(String key);

    SearchResponse QueryMatchALL();

    SearchResponse PriceRangeFilter(Integer low, Integer hight);

    SearchResponse MultiFunctionSearch(SearchInformation searchInformation);
}
