package com.test;

import com.issac.SearchApplication;
import com.issac.es.pojo.Stu;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ywy
 * @date: 2020-11-14
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class ESTest {
    @Test
    public void test() {

    }

    //
//    @Autowired
//    private ElasticsearchTemplate esTemplate;
//
//    /**
//     * 不建议使用 ElasticSearchTemplate 对索引进行管理
//     * 索引就像是数据库或数据库中的表
//     * 1. 属性(FieldType)不灵活
//     * 2. 主分片与副本分片无法设置
//     */
//    @Test
//    public void createIndexStu() {
//        Stu stu = new Stu();
//        stu.setStuId(1003L);
//        stu.setName("x man");
//        stu.setAge(31);
//        stu.setMoney(8.18f);
//        stu.setSign("i am x ");
//        stu.setDescription("I wish i was save people");
//        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
//        esTemplate.index(indexQuery);
//    }
//
//    @Test
//    public void deleteIndexStu() {
//        esTemplate.deleteIndex(Stu.class);
//    }
//
//    // -------------- 分隔 ------------
//
//    @Test
//    public void updateStuDoc() {
//        Map<String, Object> sourceMap = new HashMap<>();
//        sourceMap.put("sign", "I am not super man");
//        sourceMap.put("money", 88.6f);
//        sourceMap.put("age", "33");
//        IndexRequest indexRequest = new IndexRequest();
//        indexRequest.source(sourceMap);
//        UpdateQuery updateQuery = new UpdateQueryBuilder().withClass(Stu.class)
//                .withId("1002")
//                .withIndexRequest(indexRequest).build();
//        esTemplate.update(updateQuery);
//    }
//
//    @Test
//    public void queryDocStu() {
//        GetQuery getQuery = new GetQuery();
//        getQuery.setId("1002");
//        Stu stu = esTemplate.queryForObject(getQuery, Stu.class);
//        System.out.println(stu);
//    }
//
//    @Test
//    public void deleteDocStu() {
//        esTemplate.delete(Stu.class, "1002");
//    }
//
//    // --------------- 分隔 ------------
//
//    @Test
//    public void searchDoc() {
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        SearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("description", "save man"))
//                .withPageable(pageRequest)
//                .build();
//        AggregatedPage<Stu> pagedStu = esTemplate.queryForPage(query, Stu.class);
//        int totalPages = pagedStu.getTotalPages();
//        System.out.println("检索后的总分页数为：" + totalPages);
//        List<Stu> stuList = pagedStu.getContent();
//        for (Stu stu : stuList) {
//            System.out.println(stu);
//        }
//    }
//
//    @Test
//    public void searchDocHighlight() {
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        SortBuilder sortBuilder = new FieldSortBuilder("money").order(SortOrder.DESC);
//        SearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("description", "save man"))
//                .withHighlightFields(new HighlightBuilder.Field("description")
//                        .preTags("<span>")
//                        .postTags("</span>")
//                )
//                .withSort(sortBuilder)
//                .withPageable(pageRequest)
//                .build();
//        SearchResultMapper resultMapper = new SearchResultMapper() {
//            @Override
//            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
//                SearchHits hits = searchResponse.getHits();
//                List<Stu> stuList = new ArrayList<>();
//                for (SearchHit hit : hits) {
//                    HighlightField highlightField = hit.getHighlightFields().get("description");
//                    String description = highlightField.getFragments()[0].toString();
//                    Object stuId = hit.getSourceAsMap().get("stuId");
//                    String name = (String) hit.getSourceAsMap().get("name");
//                    Integer age = (Integer) hit.getSourceAsMap().get("age");
//                    String sign = (String) hit.getSourceAsMap().get("sign");
//                    Object money =  hit.getSourceAsMap().get("money");
//                    Stu stuHL = new Stu();
//                    stuHL.setStuId(Long.valueOf(stuId.toString()));
//                    stuHL.setName(name);
//                    stuHL.setAge(age);
//                    stuHL.setSign(sign);
//                    stuHL.setMoney(Float.valueOf(money.toString()));
//                    stuHL.setDescription(description);
//                    stuList.add(stuHL);
//                }
//                if (stuList.size() > 0) {
//                    return new AggregatedPageImpl<>((List<T>) stuList);
//                }
//                return null;
//            }
//        };
//        AggregatedPage<Stu> pagedStu = esTemplate.queryForPage(query, Stu.class, resultMapper);
//        int totalPages = pagedStu.getTotalPages();
//        System.out.println("检索后的总分页数为：" + totalPages);
//
//        List<Stu> stuList = pagedStu.getContent();
//        for (Stu stu : stuList) {
//            System.out.println(stu);
//        }
//    }
}
