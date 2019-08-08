package cn.elasticsearch.Controller;

import cn.elasticsearch.SearchInfo.SearchInformation;
import cn.elasticsearch.service.ElasticsearchService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
public class ElasticsearchController {

    @Autowired
    ElasticsearchService service;

    @RequestMapping("/testmult")
    public void testMult(SearchInformation searchInformation) {
        System.out.println(searchInformation);
    }

    @RequestMapping("/multSearch")
    public JSONArray MultiFunctionSearch(SearchInformation searchInformation) {
        System.out.println(searchInformation);
        SearchResponse searchResponse = service.MultiFunctionSearch(searchInformation);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits1) {
            jsonArray.add(hit.getSourceAsMap());
        }
        return jsonArray;

    }

    @RequestMapping("/findbyid/{id}")
    public JSONObject Findbyid(@PathVariable("id") Integer id) {

        GetResponse getResponse = service.FindbygoodsId(id);

        String index = getResponse.getIndex();
        String type = getResponse.getType();
        String gid = getResponse.getId();
        System.out.println("index-type-id");
        System.out.println("index=" + index + ", type=" + type + ", id=" + id);
        System.out.println("---------map--------------");
        Map<String, Object> source = getResponse.getSource();
        System.out.println(source);
//        System.out.println("------------bytes----------");
//        byte[] sourceAsBytes = getResponse.getSourceAsBytes();
//        System.out.println(sourceAsBytes);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(source);
        return jsonObject;
    }

    @RequestMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @RequestMapping("/matchall")
    public JSONArray matchAll() {
        SearchResponse searchResponse = service.QueryMatchALL();
        JSONArray jsonArray = new JSONArray();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            jsonArray.add(hit.getSourceAsMap());
        }
        return jsonArray;
    }

    @RequestMapping("/matchone/{key}")
    public JSONArray matchOne(@PathVariable("key") String key) {
        SearchResponse searchResponse = service.SearchGoodsInTypeAndName(key);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits1) {
            jsonArray.add(hit.getSourceAsMap());
        }
        return jsonArray;
    }
}
