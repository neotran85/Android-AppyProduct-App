package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Supplier extends RealmObject {
    @PrimaryKey
    private String id;
    private String companyName;
    private String contactTitle;
    private String logoPath;
    private Address address1;
    private Address address2;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public Address getAddress1() {
        return address1;
    }

    public void setAddress1(Address address1) {
        this.address1 = address1;
    }

    public Address getAddress2() {
        return address2;
    }

    public void setAddress2(Address address2) {
        this.address2 = address2;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
