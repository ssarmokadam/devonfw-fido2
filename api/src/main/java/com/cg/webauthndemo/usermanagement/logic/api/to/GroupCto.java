package com.cg.webauthndemo.usermanagement.logic.api.to;

import java.util.List;

import com.devonfw.module.basic.common.api.to.AbstractCto;

/**
 * Composite transport object of Group
 */
public class GroupCto extends AbstractCto {

  private static final long serialVersionUID = 1L;

  private GroupEto group;

  private List<UserEto> users;

  private List<AuthorityEto> authorities;

  public GroupEto getGroup() {

    return group;
  }

  public void setGroup(GroupEto group) {

    this.group = group;
  }

  public List<UserEto> getUsers() {

    return users;
  }

  public void setUsers(List<UserEto> users) {

    this.users = users;
  }

  public List<AuthorityEto> getAuthorities() {

    return authorities;
  }

  public void setAuthorities(List<AuthorityEto> authorities) {

    this.authorities = authorities;
  }

}
