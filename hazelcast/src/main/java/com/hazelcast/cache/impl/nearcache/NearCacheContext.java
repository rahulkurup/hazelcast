/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.cache.impl.nearcache;

import com.hazelcast.internal.serialization.SerializationService;

/**
 * Context to hold all required external services and utilities to be used by
 * {@link com.hazelcast.cache.impl.nearcache.NearCache}.
 */
public class NearCacheContext {

    private NearCacheManager nearCacheManager;
    private SerializationService serializationService;
    private NearCacheExecutor nearCacheExecutor;

    public NearCacheContext() {

    }

    public NearCacheContext(SerializationService serializationService,
                            NearCacheExecutor nearCacheExecutor) {
        this(null, serializationService, nearCacheExecutor);
    }

    public NearCacheContext(NearCacheManager nearCacheManager,
                            SerializationService serializationService,
                            NearCacheExecutor nearCacheExecutor) {
        this.nearCacheManager = nearCacheManager;
        this.serializationService = serializationService;
        this.nearCacheExecutor = nearCacheExecutor;
    }

    public NearCacheManager getNearCacheManager() {
        return nearCacheManager;
    }

    public void setNearCacheManager(NearCacheManager nearCacheManager) {
        this.nearCacheManager = nearCacheManager;
    }

    public SerializationService getSerializationService() {
        return serializationService;
    }

    public void setSerializationService(SerializationService serializationService) {
        this.serializationService = serializationService;
    }

    public NearCacheExecutor getNearCacheExecutor() {
        return nearCacheExecutor;
    }

    public void setNearCacheExecutor(NearCacheExecutor nearCacheExecutor) {
        this.nearCacheExecutor = nearCacheExecutor;
    }

}
