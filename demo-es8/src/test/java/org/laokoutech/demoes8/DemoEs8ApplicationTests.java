package org.laokoutech.demoes8;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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
        elasticsearchTemplate.createIndex();
    }

}
