/*
 *  Copyright (C) 2018 Hurence (support@hurence.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.hurence.logisland.historian.service;

import com.hurence.logisland.historian.repository.OpcRepository;
import com.hurence.logisland.historian.rest.v1.api.NotFoundException;
import com.hurence.logisland.historian.rest.v1.model.Datasource;
import com.hurence.logisland.historian.rest.v1.model.Tag;
import com.hurence.opc.da.OpcDaConnectionProfile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Spring Opc-da service.
 *
 * @author amarziali
 */
@Service
public class OpcService {


    private String extractGroupName(String tag) {
        int idx = tag.lastIndexOf('.');
        if (idx > 0) {
            return tag.substring(0, idx);
        }
        return "";
    }

    private static final Logger logger = LoggerFactory.getLogger(OpcService.class);


    private DatasourcesApiService datasourcesApiService;
    private OpcRepository opcRepository;
    private long socketTimeoutMillis;


    /**
     * Browse all tags from all available datasources.
     *
     * @return the never null list of {@link Tag}
     */
    public List<Tag> browseAllTags() {
        return datasourcesApiService.getAllDatasources("*").stream()
                .map(Datasource::getId)
                .map(this::browseDatasourceTag)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Browse all the tags available in a datasource.
     *
     * @param datasourceId the datasource to browse.
     * @return the never null list of {@link Tag}
     */
    public List<Tag> browseDatasourceTag(String datasourceId) {
        Datasource datasource = datasourcesApiService.getDatasource(datasourceId).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),
                "Unable to find datasource with id " + datasourceId));
        logger.info("Fetching tags for datasource id {}", datasourceId);
        OpcDaConnectionProfile connectionProfile = new OpcDaConnectionProfile()
                .withComClsId(datasource.getClsid())
                .withUser(datasource.getUser())
                .withDomain(datasource.getDomain())
                .withHost(datasource.getHost())
                .withPassword(datasource.getPassword())
                .withSocketTimeout(Duration.of(socketTimeoutMillis, ChronoUnit.MILLIS));
        return opcRepository.fetchAllTags(connectionProfile).stream()
                .map(tag -> new Tag()
                        .server(datasource.getHost())
                        .domain(datasource.getDomain())
                        .group(extractGroupName(tag))
                        .tagName(tag)
                        .id(StringUtils.join(new String[]{datasource.getDomain(), datasource.getHost(), tag}, "|"))
                ).collect(Collectors.toList());
    }

    @Autowired
    public void setDatasourcesApiService(DatasourcesApiService datasourcesApiService) {
        this.datasourcesApiService = datasourcesApiService;
    }

    @Autowired
    public void setOpcRepository(OpcRepository opcRepository) {
        this.opcRepository = opcRepository;
    }

    @Value("${opc.socket.timeout:2000}")
    public void setSocketTimeoutMillis(long socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;
    }
}
