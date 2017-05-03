package com.github.rmannibucau.bugmysqltomee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TheEntity {
    @GeneratedValue
    @Id
    private long id;

    public long getId() {
        return id;
    }
}
