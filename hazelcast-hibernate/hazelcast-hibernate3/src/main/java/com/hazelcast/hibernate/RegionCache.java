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

package com.hazelcast.hibernate;

import org.hibernate.cache.access.SoftLock;

import java.util.Map;

/**
 * This interface defines an internal cached region implementation as well as a mechanism
 * to unmap the cache to an underlying Map data-structure
 */
public interface RegionCache {

    Object get(final Object key, final long txTimestamp);

    boolean insert(final Object key, final Object value, final Object currentVersion);

    boolean put(final Object key, final Object value, final long txTimestamp, final Object version);

    boolean update(final Object key, final Object newValue, final Object newVersion, final SoftLock lock);

    boolean remove(final Object key);

    SoftLock tryLock(final Object key, final Object version);

    void unlock(final Object key, final SoftLock lock);

    boolean contains(final Object key);

    void clear();

    long size();

    long getSizeInMemory();

    Map asMap();

}
