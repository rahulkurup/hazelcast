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

package com.hazelcast.nio.tcp;

import com.hazelcast.nio.Packet;

import java.nio.ByteBuffer;

/**
 * A {@link WriteHandler} for the old client. It writes Packet into the ByteBuffer.
 *
 * Once the old client is deleted, this code can be deleted.
 *
 * @see OldClientReadHandler
 */
public class OldClientWriteHandler implements WriteHandler<Packet> {

    @Override
    public boolean onWrite(Packet packet, ByteBuffer dst) throws Exception {
        return packet.writeTo(dst);
    }
}
