package com.cg.webauthndemo.usermanagement.common.api;

import com.cg.webauthndemo.general.common.api.ApplicationEntity;

public interface Group extends ApplicationEntity {

  /**
   * @return idId
   */
  @Override
  public Long getId();

  /**
   * @param id setter for id attribute
   */
  @Override
  public void setId(Long id);

  /**
   * @return groupNameId
   */
  public String getGroupName();

  /**
   * @param groupName setter for groupName attribute
   */
  public void setGroupName(String groupName);

}
