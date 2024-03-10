package org.laokoutech.demoes8;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.laokoutech.demoes8.annotation.*;
import org.laokoutech.demoes8.model.CreateIndex;
import org.laokoutech.demoes8.model.DeleteIndex;
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
        CreateIndex<Project> createIndex2 = new CreateIndex<>("laokou_pro_1", "laokou_pro", Project.class);
        elasticsearchTemplate.createIndex(createIndex);
        elasticsearchTemplate.createIndex(createIndex2);
    }

    @Test
    void testDeleteIndexApi() {
        DeleteIndex deleteIndex = new DeleteIndex("laokou_res_1", "laokou_res");
        DeleteIndex deleteIndex2 = new DeleteIndex("laokou_pro_1", "laokou_pro");
        elasticsearchTemplate.deleteIndex(deleteIndex);
        elasticsearchTemplate.deleteIndex(deleteIndex2);
    }

    @Data
    @Index(analysis = @Analysis(filters = {
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
    static class Resource {

        @Field(type = Type.TEXT, searchAnalyzer = "ik_smart", analyzer = "ik_pinyin")
        private String name;

    }

    @Data
    @Index
    static class Project {
        @JsonSerialize(using = ToStringSerializer.class)
        @Field(type = Type.LONG)
        private Long businessKey;
    }

}
