package io.danubius.qhj.resource;

import io.danubius.qhj.domain.Item;
import io.danubius.qhj.dto.MultiLanguageString;
import io.danubius.qhj.service.ItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject
    ItemService itemService;

    @POST
    @Path("")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public void save(MultiLanguageString name) {
        this.itemService.save(name);
    }

    @GET
    @Path("")
    public List<Item> findById() {
        return this.itemService.findAll();
    }

}
