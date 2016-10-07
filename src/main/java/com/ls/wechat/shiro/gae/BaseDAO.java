package com.ls.wechat.shiro.gae;

import com.googlecode.objectify.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.googlecode.objectify.ObjectifyService.ofy;

public class BaseDAO<T> {
    private static final Logger log = LoggerFactory.getLogger(BaseDAO.class);

    private final Class clazz;

    public BaseDAO(Class clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings({"unchecked"})
    public T get(String id) {
        if (id == null || "".equals(id)) {
            return null;
        }
        T db = (T)ofy().load().key(Key.create(clazz, id)).now();
        return db;
    }

    public void put(T object) {
        ofy().save().entity(object).now();
    }

    @SuppressWarnings({"unchecked"})
    public void delete(String id) {
        ofy().delete().key(Key.create(clazz, id));
    }

}
