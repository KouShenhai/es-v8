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

package org.laokoutech.demoes8.annotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author laokou
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    private String name;
    private String alias;
    private List<Mapping> mappings;
    private Setting setting;
    private Analysis analysis;

    @Data
    public static class Mapping {
        private String field;
        private String type;
        private String searchAnalyzer;
        private String analyzer;
    }

    @Data
    public static class Setting {
        private short shards;
        private short replicas;
    }

    @Data
    public static class Analysis {
        private List<Option> filters;
        private List<Analyzer> analyzers;
    }

    @Data
    public static class Option {
        private String key;
        private String value;
    }

    @Data
    public static class Analyzer {
        private String filter;
        private String tokenizer;
    }

}
