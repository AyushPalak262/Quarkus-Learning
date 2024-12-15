package org.learn.quarkus.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/mobile")
public class MobileResource {

    List<String> mobileList=new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMobileList()
    {
        return Response.ok(mobileList).build();
    }
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addNewMobile(String mobileName)
    {
        mobileList.add(mobileName);
        return Response.ok(mobileName).build();
    }

    @PUT
    @Path("/{oldMobileName}")
    @Consumes(MediaType.TEXT_PLAIN)  //http://localhost:9090/mobile/IphoneX?newMobileName=Iphone16
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateMobile(@PathParam("oldMobileName") String oldMobileName,
                                 @QueryParam("newMobileName") String newMobileName )
    {
        mobileList=mobileList.stream().map(mobile->{
            if(mobile.equals(oldMobileName)){
                return newMobileName;
            }else {
                return mobile;
            }
        }).collect(Collectors.toList());

         return  Response.ok(mobileList).build();
    }

    @DELETE
    @Path("{mobileToDelete}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteMobile(@PathParam("mobileToDelete") String mobileName)
    {
        boolean isRemoved=mobileList.remove(mobileName);
        if(isRemoved)
        {
            return Response.ok(mobileList).build();
        }else {
          return  Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
