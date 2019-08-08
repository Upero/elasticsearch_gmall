package cn.elasticsearch.config;

import cn.elasticsearch.SearchInfo.SearchInformation;
import cn.elasticsearch.SearchInfo.SearchRequestInfo;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "cn.elasticsearch")
@PropertySource("classpath:elasticsearch.properties")
public class ElasticsearchConfig implements WebMvcConfigurer {

    @Value("${hostname}")
    private String hostname;
    @Value("${port}")
    private Integer port;
    @Value("${scheme}")
    private String scheme;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() throws Exception {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(
                hostname,
                port,
                scheme)));
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    @Scope("prototype")
    public SearchRequestInfo searchRequestInfo() {
        return new SearchRequestInfo();
    }

    @Bean
    @Scope("prototype")
    public SearchRequest searchRequest() {
        return new SearchRequest();
    }

    @Bean
    @Scope("prototype")
    public SearchInformation searchInformation(SearchRequest searchRequest) {
        return new SearchInformation();
    }

    @Bean
    public SearchSourceBuilder searchSourceBuilder() {
        return new SearchSourceBuilder();
    }

    @Bean
    public SearchResponse searchResponse() {
        return new SearchResponse();
    }

}
