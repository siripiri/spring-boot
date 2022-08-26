package com.siripiri.example.hibernate.relationship.mappings.example1.domain;

import javax.persistence.Entity;

@Entity
public class OrderApproval extends BaseEntity {
    private String approvedBy;

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
