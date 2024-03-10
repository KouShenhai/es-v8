package org.laokoutech.demoes8;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.laokoutech.demoes8.annotation.*;
import org.laokoutech.demoes8.model.CreateIndex;
import org.laokoutech.demoes8.template.ElasticsearchTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DemoEs8ApplicationTests {

    private final ElasticsearchClient elasticsearchClient;

    private final ElasticsearchTemplate elasticsearchTemplate;

    DemoEs8ApplicationTests(ElasticsearchClient elasticsearchClient, ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Test
    void contextLoads() {
        assertNotNull(elasticsearchClient);
    }

    @Test
    void testCreateIndexApi() {
        CreateIndex<Resource> createIndex = new CreateIndex<>("laokou_res_1", "laokou_res", Resource.class);
        elasticsearchTemplate.createIndex(createIndex);
    }

    @Data
    @Index(analysis = @Analysis(filters = {
            @Filter(option = {
                      @Option(key = "type", value = "pinyin")
                    , @Option(key = "keep_full_pinyin", value = "false")
                    , @Option(key = "keep_joined_full_pinyin", value = "true")
                    , @Option(key = "keep_original", value = "true")
                    , @Option(key = "limit_first_letter_length", value = "16")
                    , @Option(key = "remove_duplicated_term", value = "true")
                    , @Option(key = "none_chinese_pinyin_tokenize", value = "false")
            }),
    }
    , analyzers = {
            @Analyzer(filter = "laokou_pinyin", tokenizer = "ik_max_word")
    }))
    static class Resource {

        @Field(type = Type.TEXT, searchAnalyzer = "ik_smart", analyzer = "ik_pinyin")
        private String name;

    }

}
