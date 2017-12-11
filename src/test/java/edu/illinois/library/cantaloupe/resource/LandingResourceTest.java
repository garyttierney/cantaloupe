package edu.illinois.library.cantaloupe.resource;

import edu.illinois.library.cantaloupe.http.Response;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static edu.illinois.library.cantaloupe.test.Assert.HTTPAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Functional test of LandingResource.
 */
public class LandingResourceTest extends ResourceTest {

    @Override
    protected String getEndpointPath() {
        return "";
    }

    @Test
    public void testRootURIStatus() {
        assertStatus(200, getHTTPURI(""));
    }

    @Test
    public void testRootURIRepresentation() {
        assertRepresentationContains("<body", getHTTPURI(""));
    }

    @Test
    public void testResponseHeaders() throws Exception {
        client = newClient("");
        Response response = client.send();
        Map<String,String> headers = response.getHeaders();
        assertEquals(8, headers.size());

        // Accept-Ranges TODO: remove this
        assertEquals("bytes", headers.get("Accept-Ranges"));
        // Cache-Control
        assertTrue(headers.get("Cache-Control").contains("public"));
        assertTrue(headers.get("Cache-Control").contains("max-age="));
        // Content-Type
        assertEquals("text/html;charset=UTF-8", headers.get("Content-Type"));
        // Date
        assertNotNull(headers.get("Date"));
        // Server
        assertTrue(headers.get("Server").contains("Restlet"));
        // Transfer-Encoding
        assertEquals("chunked", headers.get("Transfer-Encoding"));
        // Vary
        List<String> parts = Arrays.asList(StringUtils.split(headers.get("Vary"), ", "));
        assertEquals(5, parts.size());
        assertTrue(parts.contains("Accept"));
        assertTrue(parts.contains("Accept-Charset"));
        assertTrue(parts.contains("Accept-Encoding"));
        assertTrue(parts.contains("Accept-Language"));
        assertTrue(parts.contains("Origin"));
        // X-Powered-By
        assertEquals("Cantaloupe/Unknown", headers.get("X-Powered-By"));
    }

}
