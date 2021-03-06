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

package com.hazelcast.map.impl.client;

import com.hazelcast.client.impl.client.RetryableRequest;
import com.hazelcast.client.impl.client.SecureRequest;
import com.hazelcast.map.impl.MapPortableHook;
import com.hazelcast.map.impl.MapService;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.security.permission.ActionConstants;
import com.hazelcast.security.permission.MapPermission;
import com.hazelcast.spi.OperationFactory;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Loads all given keys from a map store.
 */
public class MapLoadGivenKeysRequest extends MapAllPartitionsClientRequest implements Portable, RetryableRequest, SecureRequest {

    private List<Data> keys;

    private boolean replaceExistingValues;

    public MapLoadGivenKeysRequest() {
        keys = Collections.emptyList();
    }

    public MapLoadGivenKeysRequest(String name, List<Data> keys, boolean replaceExistingValues) {
        this.name = name;
        this.keys = keys;
        this.replaceExistingValues = replaceExistingValues;
    }

    @Override
    protected OperationFactory createOperationFactory() {
        return getOperationProvider().createLoadAllOperationFactory(name, keys, replaceExistingValues);
    }

    @Override
    protected Object reduce(Map<Integer, Object> map) {
        return null;
    }

    @Override
    public String getServiceName() {
        return MapService.SERVICE_NAME;
    }

    @Override
    public int getFactoryId() {
        return MapPortableHook.F_ID;
    }

    @Override
    public int getClassId() {
        return MapPortableHook.LOAD_ALL_GIVEN_KEYS;
    }

    @Override
    public void write(PortableWriter writer) throws IOException {
        writer.writeUTF("n", name);
        writer.writeBoolean("r", replaceExistingValues);
        final int size = keys.size();
        writer.writeInt("s", size);
        if (size > 0) {
            ObjectDataOutput output = writer.getRawDataOutput();
            for (Data key : keys) {
                output.writeData(key);
            }
        }
    }

    @Override
    public void read(PortableReader reader) throws IOException {
        name = reader.readUTF("n");
        replaceExistingValues = reader.readBoolean("r");
        final int size = reader.readInt("s");
        if (size < 1) {
            keys = Collections.emptyList();
        } else {
            keys = new ArrayList<Data>(size);
            ObjectDataInput input = reader.getRawDataInput();
            for (int i = 0; i < size; i++) {
                Data key = input.readData();
                keys.add(key);
            }
        }

    }

    @Override
    public Permission getRequiredPermission() {
        return new MapPermission(name, ActionConstants.ACTION_REMOVE);
    }

    @Override
    public String getDistributedObjectName() {
        return name;
    }

    @Override
    public String getMethodName() {
        return "loadAll";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{keys, replaceExistingValues};
    }
}
