package com.issac.es.service.impl;

import com.issac.es.pojo.Items;
import com.issac.es.service.ItemsEsService;
import com.issac.util.PagedGridResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-11-15
 * @desc:
 */
@Service
public class ItemsEsServiceImpl implements ItemsEsService {

    @Autowired
    ElasticsearchTemplate esTemplate;

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        String itemNameField = "itemName";
        SortBuilder sortBuilder = null;
        if (sort.equals("c")) {
            sortBuilder = new FieldSortBuilder("sellCounts").order(SortOrder.DESC);
        } else if (sort.equals("p")) {
            sortBuilder = new FieldSortBuilder("price").order(SortOrder.ASC);

        } else {
            sortBuilder = new FieldSortBuilder("itemName.keyword").order(SortOrder.ASC);
        }
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(itemNameField, keywords))
                .withHighlightFields(new HighlightBuilder.Field(itemNameField)
//                        .preTags("<font color='red'>")
//                        .postTags("</font>")
                )
                .withSort(sortBuilder)
                .withPageable(pageRequest)
                .build();
        SearchResultMapper resultMapper = new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                List<Items> itemsList = new ArrayList<>();
                for (SearchHit hit : hits) {
                    HighlightField highlightField = hit.getHighlightFields().get(itemNameField);
                    String itemName = highlightField.getFragments()[0].toString();
                    String itemId = (String) hit.getSourceAsMap().get("itemId");
                    String imgUrl = (String) hit.getSourceAsMap().get("imgUrl");
                    Integer price = (Integer) hit.getSourceAsMap().get("price");
                    Integer sellCounts = (Integer) hit.getSourceAsMap().get("sellCounts");

                    Items itemHL = new Items();
                    itemHL.setItemId(itemId);
                    itemHL.setItemName(itemName);
                    itemHL.setImgUrl(imgUrl);
                    itemHL.setPrice(price);
                    itemHL.setSellCounts(sellCounts);
                    itemsList.add(itemHL);
                }
                return new AggregatedPageImpl<>((List<T>) itemsList, pageable, searchResponse.getHits().totalHits);
            }
        };
        AggregatedPage<Items> pagedItems = esTemplate.queryForPage(query, Items.class, resultMapper);
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setRows(pagedItems.getContent());
        gridResult.setPage(page + 1);
        gridResult.setTotal(pagedItems.getTotalPages());
        gridResult.setRecords(pagedItems.getTotalElements());
        return gridResult;
    }
}
