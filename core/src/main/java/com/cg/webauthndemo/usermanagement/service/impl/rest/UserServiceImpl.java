package com.cg.webauthndemo.usermanagement.service.impl.rest;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.webauthndemo.usermanagement.dataaccess.api.repo.UserRepository;
import com.cg.webauthndemo.usermanagement.logic.api.UserManager;
import com.cg.webauthndemo.usermanagement.logic.api.to.UserCto;
import com.cg.webauthndemo.usermanagement.service.api.rest.UserService;

/**
 * Implementation for {@link UserService}
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  // private final UserRepository userRepository;

  private final UserManager userManager;

  @Autowired
  public UserServiceImpl(UserRepository userEntityRepository, UserManager userManager) {

    // this.userRepository = userEntityRepository;
    this.userManager = userManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserCto findOne(Long id) {

    return this.userManager.findById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<UserCto> findAll() {

    return this.userManager.findAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<UserCto> findAll(Pageable pageable) {

    return this.userManager.findAll(pageable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserCto create(UserCto userCto) {

    return this.userManager.createUser(userCto);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserCto update(Long id, Consumer<UserCto> consumer) {

    UserCto userEntity = findOne(id);
    consumer.accept(userEntity);
    this.userManager.updateUser(userEntity);
    return userEntity;
  }

  @Override
  public void delete(Long id) {

    this.userManager.deleteUser(id);

  }

}
