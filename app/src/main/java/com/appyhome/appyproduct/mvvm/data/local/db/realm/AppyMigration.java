package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class AppyMigration implements RealmMigration {
    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        //RealmSchema schema = realm.getSchema();
        //if (oldVersion == 0) {
        //RealmObjectSchema personSchema = schema.get("User");
        //personSchema.addField("token", String.class, FieldAttribute.REQUIRED);
        //oldVersion++;
        //}
    }
}
