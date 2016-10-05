package com.ls.wechat.entity;

import org.apache.shiro.crypto.hash.Sha256Hash;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static com.sun.corba.se.impl.util.RepositoryId.cache;

@Entity(name = "account")
public class Account extends BasicEntity {
    public static final int HASH_ITERATIONS = 1;
    public static final String HASH_ALGORITHM = Sha256Hash.ALGORITHM_NAME;

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private int sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
