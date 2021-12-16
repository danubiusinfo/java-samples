package io.danubius.qhj.service;

import io.danubius.qhj.domain.Item;
import io.danubius.qhj.dto.MultiLanguageString;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ItemService {

    @Transactional
    public void save(MultiLanguageString name) {
        Item item = new Item();
        item.name = name;
        item.persist();
    }

    public List<Item> findAll() {
        return Item.findAll().list();
    }

}
