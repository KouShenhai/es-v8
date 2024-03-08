/*
 * Copyright (c) 2022-2024 KCloud-Platform-Alibaba Author or Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.laokoutech.demoes8.template;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.analysis.Analyzer;
import co.elastic.clients.elasticsearch._types.analysis.CharFilter;
import co.elastic.clients.elasticsearch._types.analysis.TokenFilter;
import co.elastic.clients.elasticsearch._types.analysis.TokenFilterDefinition;
import co.elastic.clients.elasticsearch._types.mapping.DynamicMapping;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.elasticsearch.indices.IndexSettingsAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * @author laokou
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ElasticsearchTemplate {

    private final ElasticsearchClient elasticsearchClient;

    @SneakyThrows
    public void createIndex() {
        String indexName = "laokou_test_1";
        String indexAlias = "laokou_test";
        String field = "title";
        TypeMapping.Builder mappingBuilder = new TypeMapping.Builder();
        String searchAnalyzer = "ik_smart";
        String analyzer = "ik_pinyin";
        mappingBuilder.dynamic(DynamicMapping.True);
        // 分析器
        mappingBuilder.properties(field, fn -> fn.text(t -> t.eagerGlobalOrdinals(true).fielddata(true).searchAnalyzer(searchAnalyzer).analyzer(analyzer)));
        IndexSettings.Builder settingBuilder = new IndexSettings.Builder();
        IndexSettingsAnalysis.Builder settingsAnalysisBuilder = new IndexSettingsAnalysis.Builder();

        Analyzer.Builder analyzerBuilder = new Analyzer.Builder();
        analyzerBuilder.custom(fn -> fn.filter("laokou_pinyin").tokenizer("ik_max_word"));

        TokenFilter.Builder filterBuilder = getBuilder();

        settingsAnalysisBuilder.analyzer("ik_pinyin", analyzerBuilder.build());
        settingsAnalysisBuilder.filter("laokou_pinyin", filterBuilder.build());

        settingBuilder.numberOfShards("1");
        settingBuilder.numberOfReplicas("1");
        settingBuilder.analysis(settingsAnalysisBuilder.build());
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(request -> request.index(indexName)
                // 写入别名
                .aliases(indexAlias, fn -> fn.isWriteIndex(true))
                .mappings(mappingBuilder.build())
                .settings(settingBuilder.build())
        );
        boolean acknowledged = createIndexResponse.acknowledged();
        if (acknowledged) {
            log.info("索引：{} -> 创建索引成功", indexName);
        }
        else {
            log.error("索引：{} -> 创建索引失败", indexName);
        }
    }

    private static TokenFilter.Builder getBuilder() {
        TokenFilter.Builder filterBuilder = new TokenFilter.Builder();
        String str = "{\"type\":\"pinyin\"}";
        filterBuilder.definition(fn -> fn.withJson(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8))));
//        filterBuilder.definition(fn -> fn._custom("type","pinyin"));
//        filterBuilder.definition(fn -> fn._custom("keep_full_pinyin", false));
//        filterBuilder.definition(fn -> fn._custom("keep_joined_full_pinyin", true));
//        filterBuilder.definition(fn -> fn._custom("keep_original",true));
//        filterBuilder.definition(fn -> fn._custom("limit_first_letter_length",16));
//        filterBuilder.definition(fn -> fn._custom("remove_duplicated_term",true));
//        filterBuilder.definition(fn -> fn._custom("none_chinese_pinyin_tokenize",false));
        return filterBuilder;
    }

}
