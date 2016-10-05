// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        DatastoreRealm.java  (25-Oct-2011)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used,
// sold, licenced, transferred, copied or reproduced in whole or in
// part in any manner or form or in or on any media to any person
// other than in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//

package com.ls.wechat.shiro.gae;

import com.ls.wechat.entity.Account;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatastoreRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(DatastoreRealm.class.getName());


    public DatastoreRealm() {
        super(new MemcacheManager(), theCredentials());

        log.debug("Creating a new instance of DatastoreRealm");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = ((UsernamePasswordToken) token).getUsername();
        return doGetAuthenticationInfo(userName);
    }

    private AuthenticationInfo doGetAuthenticationInfo(String userName) throws AuthenticationException {
//        Preconditions.checkNotNull(userName, "User name can't be null");

        log.debug("Finding authorization info for {} in DB", userName);
        Account user = new Account();
        user.setUsername(userName);

        if (user == null || userIsNotQualified(user)) {
            log.info("Rejecting " + user.getName());
            return null;
        }
        log.info("Found {} in DB", userName);

        SimpleAccount account = new SimpleAccount(userName, userName.getBytes(), this.getName());

//        SimpleAccount account = new SimpleAccount(user.getName(),
//                user.getPasswordHash(), new SimpleByteSource(user.getSalt()), getName());
//        account.setRoles(user.getRoles());
//        account.setStringPermissions(user.getPermissions());
        return account;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        Preconditions.checkNotNull(principals, "You can't have a null collection of principals");

        String userName = (String) getAvailablePrincipal(principals);
        if (userName == null) {
            throw new NullPointerException("Can't find a principal in the collection");
        }
        log.debug("Finding authorization info for {} in DB", userName);
//        Account user = dao().findUser(userName);
        Account user = new Account();
        user.setUsername(userName);
        if (user == null || userIsNotQualified(user)) {
            return null;
        }
        log.debug("Found {} in DB", userName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(user.getRoles());
//        info.setStringPermissions(user.getPermissions());
        return info;
    }

    private static CredentialsMatcher theCredentials() {
        HashedCredentialsMatcher credentials = new HashedCredentialsMatcher(Account.HASH_ALGORITHM);
        credentials.setHashIterations(Account.HASH_ITERATIONS);
        credentials.setStoredCredentialsHexEncoded(true);
        return credentials;
    }

    private static boolean userIsNotQualified(Account user) {
//        return !user.isRegistered() || user.isSuspended();
        return true;
    }
}
