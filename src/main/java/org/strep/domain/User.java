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
package org.strep.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NaturalId;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//import java.util.HashSet;
import java.util.Objects;
import org.strep.validator.FieldMatch;
//import java.util.Set;

/**
 * JPA Bean for the User objects managed by application
 *
 * @author Ismael Vázquez
 */
@Entity
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class User implements Serializable {

    /**
     * The default serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The username of the user
     */
    @NaturalId
    @Id
    @NotNull(message = "Name of the user cannot be null")
    @Size(min = 1, max = 30, message = "Username must have beetween 1 and 30 characters")
    @Column(length = 30, columnDefinition = "VARCHAR(30)")
    private String username;

    /**
     * The email of the user
     */
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9\\._%+-]+@[A-Za-z0-9\\.-]+\\.[A-Za-z]{2,6}$", message = "The e-mail is not well formed")
    private String email;

    /**
     * The password of the user (decripted)
     */
    @Size(min = 8, max = 30, message = "Password must have beetween 8 and 30 characters")
    @Transient 
    private String password;
    /**
     * The confirmation of password (decripted)
     */
    @Size(min = 8, max = 30, message = "Password must have beetween 8 and 30 characters")
    @Transient 
    private String confirmPassword;

    /**
     * The encripted password (Will be saved in BBDD)
     */
    @Column(name="encrypted_password", length = 255, columnDefinition = "VARCHAR(255)")
    private String encryptedPassword;

    /**
     * The name of the user
     */
    @Column(length = 100, columnDefinition = "VARCHAR(100)")
    @Size(max = 100, message = "The name of the user must have less than 100 characters")
    private String name;

    /**
     * The surname of the user
     */
    @Column(length = 100, columnDefinition = "VARCHAR(100)")
    @Size(max = 100, message = "The surname of the user must have less than 100 characters")
    private String surname;

    /**
     * The activation field for confirmed accounts
     */
    private boolean confirmedAccount;

    /**
     * The hash of the user
     */
    private String hash;

    /**
     * The photo of the user
     */
    @Lob
    private byte[] photo;

    /**
     * The permissions of the user
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Permission permission;

    /**
     * The permission requests
     */
    //@OneToMany(fetch=FetchType.EAGER, mappedBy="user")
    //private Set<PermissionRequest> permissionRequests = new HashSet<PermissionRequest>();

    /**
     * The default constructor
     */
    protected User() {
    }

    /**
     * Create instances of the user
     *
     * @param username the username of the user
     * @param email the email of the user
     * @param hash the hash of the user
     * @param password the password of the user
     * @param name the name of the user
     * @param surname the surname of the user
     */
    public User(String username, String email, String hash, String password, String name, String surname) {
        this.username = username;
        this.email = email;
        this.hash = hash;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
    
    /**
     * Create instances of the user
     *
     * @param username the username of the user
     * @param email the email of the user
     * @param hash the hash of the user
     * @param password the password of the user
     * @param confirmPassword the password of the user
     * @param name the name of the user
     * @param surname the surname of the user
     */
    public User(String username, String email, String hash, String password,String confirmPassword, String name, String surname) {
        this.username = username;
        this.email = email;
        this.hash = hash;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.surname = surname;
    }

    /**
     * Return the username of the user
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Stablish the username of the user
     *
     * @param username the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return the email of the user
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Stablish the email of the user
     *
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the password of the user
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Stablish the password of the user
     *
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Return the the confirmation of user password
     *
     * @return the confirmation of user password
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Stablish the confirmation of the user password
     *
     * @param confirmPassword the confirmation of user password
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    /**
     * Return the name of the user
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Stablish the name of the user
     *
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the surname of the user
     *
     * @return the surname of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Stablish the surname of a user
     *
     * @param surname The username of the user
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Return the photo of the user
     *
     * @return the photo of the user
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * Stablish the photo of the user
     *
     * @param photo the photo of the user
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    /**
     * Return the permissions of the user
     *
     * @return the permissions of the user
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Stablish the permissions of the user
     *
     * @param permissions the permissions of the user
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Return the activation field of the user
     *
     * @return the activation field of the user
     */
    public boolean getConfirmedAccount() {
        return confirmedAccount;
    }

    /**
     * Stablish the activation field of the user
     *
     * @param confirmedAccount the activation field of the user
     */
    public void setConfirmedAccount(boolean confirmedAccount) {
        this.confirmedAccount = confirmedAccount;
    }

    /**
     * Return the hash of the user
     *
     * @return the hash of the user
     */
    public String getHash() {
        return hash;
    }

    /**
     * Stablish the hash of the user
     *
     * @param hash the hash of the user
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Return the users wich have the permission
     *
     * @return the users wich have the permission
     */
    //public Set<PermissionRequest> getPermissionRequests() {
    //    return permissionRequests;
    //}

    /**
     * Stablish the users wich have the permission
     *
     * @param users the users wich have the permission
     */
    //public void setPermissionRequests(Set<PermissionRequest> permissionRequests) {
    //    this.permissionRequests = permissionRequests;
    //}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    /**
     * Returns the encripted password for the user
     * @return the encripted password for the user 
     */
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    /**
     * Stablish the encripted password for the user
     * @param encryptedPassword The encripted password for the user
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
