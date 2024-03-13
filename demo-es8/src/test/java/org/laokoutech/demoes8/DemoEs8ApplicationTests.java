package org.laokoutech.demoes8;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.IndexState;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.laokoutech.demoes8.annotation.*;
import org.laokoutech.demoes8.template.ElasticsearchTemplate;
import org.laokoutech.demoes8.utils.JacksonUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DemoEs8ApplicationTests {

    private final ElasticsearchClient elasticsearchClient;

    private final ElasticsearchTemplate elasticsearchTemplate;

    private final ElasticsearchAsyncClient elasticsearchAsyncClient;

    DemoEs8ApplicationTests(ElasticsearchClient elasticsearchClient, ElasticsearchTemplate elasticsearchTemplate, ElasticsearchAsyncClient elasticsearchAsyncClient) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.elasticsearchAsyncClient = elasticsearchAsyncClient;
    }

    @Test
    void contextLoads() {
        assertNotNull(elasticsearchClient);
        assertNotNull(elasticsearchAsyncClient);
    }

    @Test
    void testCreateIndexApi() {
        elasticsearchTemplate.createIndex("laokou_res_1", "laokou_res", Resource.class);
        elasticsearchTemplate.createIndex("laokou_pro_1", "laokou_pro", Project.class);
    }

    @Test
    void testAsyncCreateDocumentApi() {
        elasticsearchTemplate.asyncCreateDocument("laokou_res_1","444", new Resource("8888"));
    }

    @Test
    void testAsyncBulkCreateDocumentApi() {
        elasticsearchTemplate.asyncBulkCreateDocument("laokou_res_1",Map.of("555",new Resource("6666")));
    }

    @Test
    void testCreateDocumentApi() {
        elasticsearchTemplate.createDocument("laokou_res_1","222", new Resource("3333"));
    }

    @Test
    void testBulkCreateDocumentApi() {
        elasticsearchTemplate.bulkCreateDocument("laokou_res_1",Map.of("333",new Resource("5555")));
    }

    //@Test
    void testGetIndexApi() {
        Map<String, IndexState> result = elasticsearchTemplate.getIndex(List.of("laokou_res_1", "laokou_pro_1"));
        log.info("索引信息：{}", JacksonUtil.toJsonStr(result));
    }

    @Test
    void testDeleteIndexApi() {
        elasticsearchTemplate.deleteIndex(List.of("laokou_res_1", "laokou_pro_1"));
    }

    @Data
    @Index(setting = @Setting(refreshInterval = "-1")
            ,analysis = @Analysis(filters = {
            @Filter(name = "laokou_pinyin",options = { @Option(key = "type", value = "pinyin")
                    , @Option(key = "keep_full_pinyin", value = "false")
                    , @Option(key = "keep_joined_full_pinyin", value = "true")
                    , @Option(key = "keep_original", value = "true")
                    , @Option(key = "limit_first_letter_length", value = "16")
                    , @Option(key = "remove_duplicated_term", value = "true")
                    , @Option(key = "none_chinese_pinyin_tokenize", value = "false")
            }),
    }
    , analyzers = {
            @Analyzer(name = "ik_pinyin", args = @Args(filter = "laokou_pinyin", tokenizer = "ik_max_word"))
    }))
    @NoArgsConstructor
    @AllArgsConstructor
    static class Resource implements Serializable {

        @Field(type = Type.TEXT, searchAnalyzer = "ik_smart", analyzer = "ik_pinyin")
        private String name;

    }

    @Data
    @Index
    static class Project implements Serializable{
        @JsonSerialize(using = ToStringSerializer.class)
        @Field(type = Type.LONG)
        private Long businessKey;
    }

}
