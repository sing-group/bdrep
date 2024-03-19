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
package org.strep.repositories;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import org.strep.domain.Dataset;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations
 * with added queries for Dataset objects
 *
 * @author Ismael Vázquez
 */
public interface DatasetRepository extends CrudRepository<Dataset, String> {

    /**
     * Return available datasets with public visibility
     *
     * @return Set of available datasets with public visibility
     */
    @Query(value = "SELECT * FROM dataset WHERE access='public' AND available=true AND name IN (SELECT dataset_name FROM task WHERE state='success')", nativeQuery = true)
    public HashSet<Dataset> getPublicDatasets();

    /**
     * Return available system datasets
     *
     * @return List of available system datasets
     */

    
    @Query(value ="SELECT * FROM dataset WHERE type='systemdataset' AND available=true AND name IN (SELECT dataset_name FROM task WHERE state='success')", nativeQuery = true)
    public ArrayList<Dataset> getSystemDatasets();

    /**
     * Return available protected datasets
     *
     * @return List of available protected datasets
     */
    @Query(value = "SELECT * FROM dataset WHERE access='protected' AND available=true", nativeQuery = true)
    public ArrayList<Dataset> getProtectedDatasets();

    /**
     * Return available datasets for the specified author
     *
     * @param username the username of the author of the dataset
     * @return A list of the available datasets for this author
     */
    @Query(value = "SELECT * FROM dataset WHERE author=?1 AND available=true", nativeQuery = true)
    public ArrayList<Dataset> getUserDatasets(String username);

    /**
     * Return available datasets filtered by author and type
     *
     * @param username the username of the author of the dataset
     * @param type     the type of the dataset: spam or ham
     * @return A list of the available datasets for this filters
     */
    /*
     * @Query(
     * value="SELECT * FROM dataset WHERE author=?1 AND type=?2 AND available=true",
     * nativeQuery=true)
     */
    @Query(value = "SELECT * FROM dataset WHERE (author=?1 OR access='public') AND available=true AND type=?2", nativeQuery = true)
    public ArrayList<Dataset> getUserDatasets(String username, String type);

    /**
     * Return available datasets owned by author and filtered by type
     *
     * @param username the username of the author of the dataset
     * @param type     the type of the dataset: userdataset or systemdataset
     * @return A list of available datasets owned by author AND filtered by type
     */

    @Query(value = "SELECT * FROM dataset WHERE type=?2 AND available=true AND author=?1 AND name IN (SELECT dataset_name FROM task WHERE state='success')", nativeQuery = true)
    public ArrayList<Dataset> getOwnDatasets(String username, String type);

    /**
     * Return not private and available datasets owned by any user but author,
     * filtered by type
     *
     * @param username the username of the author of the dataset
     * @param type     the type of the dataset: userdataset or systemdataset
     * @return A list of not private and available datasets owned by any user but
     *         author, filtered by type
     */
    
    @Query(value = "SELECT * FROM dataset WHERE type=?2 AND available=true AND author<>?1 AND access<>'private' AND name IN (SELECT dataset_name FROM task WHERE state='success')", nativeQuery = true)
    public ArrayList<Dataset> getCommunityDatasets(String username, String type);

    /**
     * Return all system datasets owned by author and not private owned by anyone
     *
     * @param username the username of the author of the dataset
     * @param type     the type of the dataset: Dataset.TYPE_USER or
     *                 Dataset.TYPE_SYSTEM
     * @return A list datasets owned by author and not private owned by anyone
     */
    
     @Query(value =    "SELECT * FROM dataset WHERE type=?2 AND available=true AND (author=?1 OR access<>'private') AND name IN (SELECT dataset_name FROM task WHERE state='success')", nativeQuery = true)
   
   
    public ArrayList<Dataset> getSystemDatasets(String username, String type);

    /**
     * Return file ids that are associated with the specified dataset
     *
     * @param name the name of the dataset
     * @return A list of ids for the files of the specified dataset
     */
    @Query(value = "SELECT file_id FROM dataset_files WHERE dataset_name=?1", nativeQuery = true)
    public ArrayList<BigInteger> getFileIds(String name);

    /**
     * Return filtered system datasets that meet the marked requirements of
     * languages, datatypes and license
     *
     * @param languages the valid languages
     * @param datatypes the valid datatypes
     * @param license   the valid licenses
     * @return a list of the system datasets that meet the requirements
     */
    @Query(value = "SELECT distinct(d.name) FROM dataset d INNER JOIN dataset_languages lang ON d.name=lang.dataset_name INNER JOIN dataset_datatypes data ON d.name=data.dataset_name WHERE d.type='systemdataset' AND lang.language IN ?1 AND data.data_type IN ?2 AND d.id IN ?3", nativeQuery = true)
    public ArrayList<String> getFilteredDatasets(Collection<String> languages, Collection<String> datatypes,
            Collection<String> license);

    /**
     * Return filtered system datasets that meet the marked requirements of
     * languages, datatypes, license, initial messages date and final messages date
     *
     * @param languages the valid languages
     * @param datatypes the valid datatypes
     * @param license   the valid license
     * @param date1     the valid initial date
     * @param date2     the valid final date
     * @return a list of system datasets that meet the requirements
     */
    @Query(value = "SELECT distinct(d.name) FROM dataset d INNER JOIN dataset_languages lang ON d.name=lang.dataset_name INNER JOIN dataset_datatypes data ON d.name=data.dataset_name "
            + " WHERE d.type='systemdataset' AND lang.language IN ?1 AND data.data_type IN ?2 AND d.id IN ?3 AND (d.first_file_date <= ?5 AND d.last_file_date >= ?4)", nativeQuery = true)
    public ArrayList<String> getFilteredDatasetsByDate(Collection<String> languages, Collection<String> datatypes,
            Collection<String> license, String date1, String date2);

    /**
     * Return the datatypes associated to the list of specified datasets
     *
     * @param datasetNames the names of the datasets
     * @return A list of the datatypes associated to those datasets
     */
    @Query(value = "SELECT distinct(data_type) FROM dataset_datatypes WHERE dataset_name IN (?1)", nativeQuery = true)
    public ArrayList<String> getDatasetsDatatypes(Collection<String> datasetNames);

    /**
     * Return the number of spam/ham files associated to specified datasets
     *
     * @param datasetNames the name the datasets
     * @param type
     * @return the number of spam files associated to those datasets
     */
    @Query(value = "SELECT count(distinct(df.file_id)) FROM dataset d INNER JOIN dataset_files df ON d.name=df.dataset_name  INNER JOIN file f ON df.file_id=f.id WHERE d.name IN (?1) AND f.type=?2", nativeQuery = true)
    public int countFilesByType(Collection<String> datasetNames, String type);

    /**
     * Return the count of files by extension AND type associated to the specified
     * datasets
     *
     * @param datasetNames the name of the datasets
     * @return the count of the files by extension AND type associated to those
     *         datasets
     */
    @Query(value = "SELECT COUNT(f.id) FROM dataset d INNER JOIN dataset_files df ON d.name=df.dataset_name INNER JOIN file f ON df.file_id=f.id WHERE d.name IN(?1) GROUP BY f.extension, f.type", nativeQuery = true)
    public ArrayList<Object> getFilesByExtensionAndType(Collection<String> datasetNames);

    /**
     * Return datasets by name
     *
     * @param name the name of the dataset
     * @return A list of available datasets owned by author AND filtered by type
     */
    @Query(value = "SELECT * FROM dataset WHERE name=?1", nativeQuery = true)
    public Dataset findDatasetByName(String name);

    /**
     * Check if combining a list of datasets requires attribution
     * 
     * @param datasetNames The datasets to be combined
     * @return true if citation is required for combine the SELECTed datasets
     */
    @Query(value = "SELECT SUM(IF(attribute_required,1,0)) FROM license,dataset WHERE license.name=dataset.id AND dataset.name IN (?1)", nativeQuery = true)
    public int checkIfCombinationRequiresAttribution(Collection<String> datasetNames);

    /**
     * Combine citations of some datasets into one single string
     * 
     * @param datasetNames The datasets to combine citations
     * @return The String concatenation of datasets
     */
    @Query(value = "SELECT GROUP_CONCAT(citation_request,IF(citation_request!=null, '.','') SEPARATOR '') FROM dataset WHERE dataset.name IN (?1)", nativeQuery = true)
    public String combineAllCitations4Datasets(Collection<String> datasetNames);

    /**
     * Check if redistribution of combination of some datasets (as parameter) is not
     * allowed
     * 
     * @param datasetNames The datasets that is going to be combined
     * @return true if redistribution is not allowed
     */
    @Query(value = "SELECT SUM(IF(redistribute,0,1)) FROM license,dataset WHERE license.name=dataset.id AND dataset.name IN (?1)", nativeQuery = true)
    public int checkIfRedistributionIsNotAllowed(Collection<String> datasetNames);

    /**
     * Get datasets filtered by Name
     * 
     * @param datasetNames The datasets that is going to be combined
     * @return The list of datasets filtered by name
     */
    @Query(value = "SELECT dataset.* FROM dataset WHERE dataset.name IN (?1)", nativeQuery = true)
    public ArrayList<Dataset> findDatasetsByNames(Collection<String> datasetNames);

}
