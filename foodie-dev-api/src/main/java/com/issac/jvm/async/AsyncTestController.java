package com.issac.jvm.async;

import com.issac.pojo.bo.AddressBO;
import com.issac.util.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

/**
 * @author: ywy
 * @date: 2021-02-17
 * @desc:
 */
@RestController
public class AsyncTestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTestController.class);

    @Autowired
    private AsyncDemoAsyncAnnotation asyncDemoAsyncAnnotation;

    @GetMapping("/async-test")
    public String asyncTest() throws ExecutionException, InterruptedException {
        asyncDemoAsyncAnnotation.biz();
        return "success";
    }

    @Autowired
    AsyncRestTemplate asyncRestTemplate;

    @GetMapping("/test-async-template")
    public String testAsyncRestTemplate() {
        ListenableFuture<ResponseEntity<JSONResult>> future =
                this.asyncRestTemplate.getForEntity(
                        "http://localhost:8088/index/subCat/{rootCatId}",
                        JSONResult.class,
                        1
                );

//        ResponseEntity<JSONResult> entity = future.get();
//        JSONResult body = entity.getBody();
        future.addCallback(new ListenableFutureCallback<ResponseEntity<JSONResult>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.error("请求失败", throwable);
            }

            @Override
            public void onSuccess(ResponseEntity<JSONResult> jsonResultResponseEntity) {
                LOGGER.info("调用成功，body = {}", jsonResultResponseEntity.getBody().getData());
            }
        });
        return "success";
    }

    @GetMapping("/test-async-rest-template-post")
    public JSONResult testAsyncRestTemplatePost() throws ExecutionException, InterruptedException {
        AddressBO addressBO = new AddressBO();
        addressBO.setAddressId("111");
        addressBO.setUserId("111");
        addressBO.setReceiver("111");
        addressBO.setMobile("15151816012");
        addressBO.setProvince("aaa");
        addressBO.setDistrict("aaa");
        addressBO.setDetail("aaa");
        addressBO.setCity("nj");

        ListenableFuture<ResponseEntity<JSONResult>> future =
                this.asyncRestTemplate.postForEntity(
                        "http://localhost:8088/address/update",
                        new HttpEntity<>(addressBO),
                        JSONResult.class
                );
        ResponseEntity<JSONResult> entity = future.get();
        return entity.getBody();
    }

    @Autowired
    WebClient webClient;

    @GetMapping("/test-web-client")
    public JSONResult testWebClient() {
        Mono<JSONResult> mono = webClient.get()
                .uri("http://localhost:8088/index/subCat/{rootCatId}", 1)
                .retrieve()
                .bodyToMono(JSONResult.class);
        JSONResult result = mono.block();
        return result;
    }

    @GetMapping("/test-web-client-post")
    public JSONResult testWebClientPost() throws ExecutionException, InterruptedException {
        AddressBO addressBO = new AddressBO();
        addressBO.setAddressId("111");
        addressBO.setUserId("111");
        addressBO.setReceiver("111");
        addressBO.setMobile("15151816012");
        addressBO.setProvince("aaa");
        addressBO.setDistrict("aaa");
        addressBO.setDetail("aaa");
        addressBO.setCity("nj");

        Mono<JSONResult> mono = this.webClient.post()
                .uri("http://localhost:8088/address/update")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(addressBO))
                .retrieve()
                .bodyToMono(JSONResult.class);
        return mono.block();
    }
}
