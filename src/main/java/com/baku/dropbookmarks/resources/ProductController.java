package com.baku.dropbookmarks.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Path("/bk")
public class ProductController {

    @Path("/home/product")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response submitProduct(@Context HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error reading request body")
                    .build();
        }
        log.info("MESSAGE BODY RECEIVED IN POST REQUEST \n{}",requestBody);
        return Response.status(200).entity(requestBody.toString()).build();
    }
}
