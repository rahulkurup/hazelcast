package com.hazelcast.internal.management;


import com.eclipsesource.json.JsonObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.Node;
import com.hazelcast.internal.management.request.ChangeWanStateRequest;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class ChangeWanStateRequestTest extends HazelcastTestSupport {

    private HazelcastInstance hz;
    private ManagementCenterService managementCenterService;

    @Before
    public void setUp() {
        hz = createHazelcastInstance();
        Node node = getNode(hz);
        managementCenterService = node.getManagementCenterService();
    }

    @Test
    public void testResumingWanState() throws Exception {
        ChangeWanStateRequest changeWanStateRequest = new ChangeWanStateRequest("schema", "publisher", true);
        JsonObject jsonObject = new JsonObject();
        changeWanStateRequest.writeResponse(managementCenterService, jsonObject);

        JsonObject result = (JsonObject) jsonObject.get("result");
        assertNotEquals(ChangeWanStateRequest.SUCCESS, changeWanStateRequest.readResponse(result));
    }

    @Test
    public void testPausingWanState() throws Exception {
        ChangeWanStateRequest changeWanStateRequest = new ChangeWanStateRequest("schema", "publisher", false);
        JsonObject jsonObject = new JsonObject();
        changeWanStateRequest.writeResponse(managementCenterService, jsonObject);

        JsonObject result = (JsonObject) jsonObject.get("result");
        assertNotEquals(ChangeWanStateRequest.SUCCESS, changeWanStateRequest.readResponse(result));
    }
}