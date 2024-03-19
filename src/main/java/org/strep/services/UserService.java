/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.strep.services;

import org.strep.repositories.PermissionRepository;
import org.strep.repositories.UserRepository;

import org.strep.domain.User;
import org.strep.domain.Permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service class to abstract to the controller of user tasks not related to the
 * presentation
 *
 * @author Ismael Vázquez
 */
@Service("userService")
public class UserService {

    /**
     * The repository to access user data
     */
    private UserRepository userRepository;

    /**
     * The repository to access permission data
     */
    private PermissionRepository permissionRepository;

    /**
     * The password encoder object
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The message i18n
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * The constructor for create instances of UserService
     *
     * @param userRepository the user repository
     * @param permissionRepository the permission repository
     * @param bCryptPasswordEncoder the password encoder
     */
    @Autowired
    public UserService(UserRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Save the specified user, in case of new user
     *
     * @param user the specified user
     */
    public void createUser(User user) {
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmedAccount(false);

        Optional<Permission> opt = permissionRepository.findById(new Long(1));

        if (opt.isPresent()) {
            Permission permission = opt.get();
            user.setPermission(permission);

            userRepository.save(user);
        }
    }

    /**
     * Update the specified user
     *
     * @param user the specified user to update
     */
    public void updateUser(User user) {
        user.setConfirmedAccount(true);
        Optional<User> optUser = userRepository.findById(user.getUsername());

        if (optUser.isPresent()) {
            User u = optUser.get();

            if (user.getPassword().equals(u.getEncryptedPassword().substring(0, 29))) {
                user.setEncryptedPassword(u.getEncryptedPassword());
            } else {
                user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }

            user.setPermission(u.getPermission());
            userRepository.save(user);
        }
    }

    /**
     * Return the max authority for the specified user
     *
     * @param username the user username
     * @return the max authority for the specified user
     */
    public String getPermissionsByUsername(String username) {
        try {
            return convertPermIdToString(userRepository.findById(username).get().getPermission().getId());
        } catch (NoSuchElementException e) {
            return convertPermIdToString(1L);
        }
    }

    /**
     * Edit the permissions for the specified user
     *
     * @param permissionIntId the permission id
     * @param username the user username
     * @return a description message for the performed operations
     */
    public String editPermissions(int permissionIntId, String username) {
        Locale locale = LocaleContextHolder.getLocale();

        String message = null;

        Optional<Permission> permissionOpt = permissionRepository.findById(new Long(permissionIntId));
        Optional<User> userOpt = userRepository.findById(username);
        if (permissionOpt.isPresent() && userOpt.isPresent()) {
            Permission permission = permissionOpt.get();
            User user = userOpt.get();
            user.setPermission(permission);
            userRepository.save(user);

            message = messageSource.getMessage("edit.permission.sucess", Stream.of().toArray(String[]::new), locale);
        } else {
            message = messageSource.getMessage("edit.permission.fail", Stream.of().toArray(String[]::new), locale);
        }

        return message;
    }

    /**
     * This method uploads a photo of the specified user
     *
     * @param username the user username
     * @param multipartFile the uploaded photo
     */
    public void editProfile(String username, MultipartFile multipartFile) {
        Optional<User> optUser = userRepository.findById(username);

        if (optUser.isPresent()) {
            try {
                User user = optUser.get();
                user.setPhoto(multipartFile.getBytes());
                userRepository.save(user);
            } catch (Exception e) {

            }
        }

    }

    /**
     * Auxiliar method to convert perm id to String
     *
     * @param id the id of the max permission of the user
     * @return perm id converted to String
     */
    private String convertPermIdToString(Long id) {
        switch (id.intValue()) {
            case 1:
                return Permission.VIEW;
            case 2:
                return Permission.CREATE_CORPUS;
            case 3:
                return Permission.UPLOAD;
            case 4:
                return Permission.ADMINISTER;
            default:
                return Permission.VIEW;
        }
    }

}
