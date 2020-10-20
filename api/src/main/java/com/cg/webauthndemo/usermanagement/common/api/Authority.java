package com.cg.webauthndemo.usermanagement.common.api;

import com.cg.webauthndemo.general.common.api.ApplicationEntity;

public interface Authority extends ApplicationEntity {

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
   * @return authorityId
   */
  public String getAuthority();

  /**
   * @param authority setter for authority attribute
   */
  public void setAuthority(String authority);

}
