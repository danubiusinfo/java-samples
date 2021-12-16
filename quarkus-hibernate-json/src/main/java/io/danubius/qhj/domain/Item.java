package io.danubius.qhj.domain;

import io.danubius.qhj.dto.MultiLanguageString;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Item extends PanacheEntity {

    @Type(type = "io.danubius.qhj.persistence.type.MultiLanguageStringType")
    @Column(name = "name", columnDefinition = "json")
    public MultiLanguageString name;
}
