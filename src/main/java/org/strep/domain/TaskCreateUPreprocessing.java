
package org.strep.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázquez
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class TaskCreateUPreprocessing extends Task
{
    /**
     * The name of the preprocesssing (for reutilization purposes)
     */
    @NotNull
    @Size(min=1, max=100, message="The preprocessing name must have beetween 1 and 100 characters")
    @Column(length = 100, columnDefinition="VARCHAR(100)")
    @Pattern(regexp = "^[A-Za-z0-9\\-_]*$", message = "The name of preprocessing should contain only alphanumeric characters. Additionally _ and - characters are also permitted.")
    private String name;

    /**
     * A description for the preprocessing task
     */
    @Lob
    private String description;

    /**
     * The pipeline used to preprocess the dataset
     */
    @Lob
    private byte[] pipeline;

    /**
     * The name of the csv to identify in the filesystem
     */
    @Lob
    private String csv;

    /**
     * The date of creation of the preprocessing task
     */
    private Date date;

    /**
     * The dataset to preprocess
     */
    @OneToOne
    @PrimaryKeyJoinColumn(name="dataset_name", referencedColumnName="name")
    private Dataset preprocessDataset;

    /**
     * The default constructor
     */
    public TaskCreateUPreprocessing()
    {
        super();
    }

    /**
     * Creates an instance of TaskCreateUPreprocessing 
     * @param dataset the dataset associated to the task
     * @param state the state of the task: waiting, failed, success, executing
     * @param message the message of the task, only if the task was failed
     * @param description the description of the task
     * @param pipeline the pipeline used to preprocess the dataset
     * @param csv the name of the csv generated
     * @param date the date of upload of the task
     */
    public TaskCreateUPreprocessing(Dataset dataset, String name, String state, String message, String description, byte[] pipeline, String csv, Date date, Dataset preprocessDataset)
    {
        super(dataset, state, message);
        this.name=name;
        this.description = description;
        this.pipeline = pipeline;
        this.csv = csv;
        this.date = date;
        this.preprocessDataset = preprocessDataset;
    }

    /**
     * Return the description of the task
     * @return the description of the task
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Stablish the description of the task
     * @param description the description of the task
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Return the pipeline of the task
     * @return the pipeline of the task
     */
    public byte[] getPipeline()
    {
        return this.pipeline;
    }

    /**
     * Stablish the pipeline of the task
     * @param pipeline the pipeline of the task
     */
    public void setPipeline(byte[] pipeline)
    {
        this.pipeline = pipeline;
    }

    /**
     * Return the name of the csv generated by the task
     * @return the name of the csv generated by the task
     */
    public String getCsv()
    {
        return this.csv;
    }

    /**
     * Stablish the name of the csv generated by the task
     * @param csv the name of the csv generated by the task
     */
    public void setCsv(String csv)
    {
        this.csv = csv;
    }

    /**
     * Return the date of the task
     * @return the date of the task
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Stablish the date of the task
     * @param date the date of the task
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * Return the preprocessed dataset
     * @return the preprocessed dataset
     */
    public Dataset getPreprocessDataset()
    {
        return this.preprocessDataset;
    }

    /**
     * Stablish the preprocessed dataset
     * @param preprocessDataset the preprocessed dataset
     */
    public void setPreprocessDataset(Dataset preprocessDataset)
    {
        this.preprocessDataset = preprocessDataset;
    }

    /**
     * Retrieves the name of the preprocessing
     * @return The name of the preprocessing
     */
    public String getName() {
        return name;
    }

    /**
     * Stablish the name of the preprocessing
     * @param name The name of the preprocessing
     */
    public void setName(String name) {
        this.name = name;
    }

    
}