package org.laokoutech.demoes8;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class DemoEs8ApplicationTests {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Test
    void contextLoads() {
        assertNotNull(elasticsearchClient);
    }

    @Test
    void testCreateIndexApi() {

    }

}
