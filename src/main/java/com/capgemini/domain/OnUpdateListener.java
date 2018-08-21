package com.capgemini.domain;

import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.util.Date;

public class OnUpdateListener {

    @PreUpdate
    protected void onUpdate(final AbstractEntity abstractEntity) {
        Date updateDate= new Date();
        abstractEntity.setUpdated(new Timestamp(updateDate.getTime()));
    }

}
