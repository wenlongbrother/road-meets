package com.qinfenfeng.roadmeets.mbg.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.id
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.gmt_create
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private Date gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.gmt_modified
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private Date gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.content
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.scene
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private String scene;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.readed
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private Byte readed;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.speaker
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private Long speaker;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.deleted
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private Byte deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table message
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.id
     *
     * @return the value of message.id
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.id
     *
     * @param id the value for message.id
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.gmt_create
     *
     * @return the value of message.gmt_create
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.gmt_create
     *
     * @param gmtCreate the value for message.gmt_create
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.gmt_modified
     *
     * @return the value of message.gmt_modified
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.gmt_modified
     *
     * @param gmtModified the value for message.gmt_modified
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.content
     *
     * @return the value of message.content
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.content
     *
     * @param content the value for message.content
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.scene
     *
     * @return the value of message.scene
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public String getScene() {
        return scene;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.scene
     *
     * @param scene the value for message.scene
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public void setScene(String scene) {
        this.scene = scene;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.readed
     *
     * @return the value of message.readed
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public Byte getReaded() {
        return readed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.readed
     *
     * @param readed the value for message.readed
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public void setReaded(Byte readed) {
        this.readed = readed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.speaker
     *
     * @return the value of message.speaker
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public Long getSpeaker() {
        return speaker;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.speaker
     *
     * @param speaker the value for message.speaker
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public void setSpeaker(Long speaker) {
        this.speaker = speaker;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.deleted
     *
     * @return the value of message.deleted
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public Byte getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.deleted
     *
     * @param deleted the value for message.deleted
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbggenerated Mon Jan 13 15:22:13 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", content=").append(content);
        sb.append(", scene=").append(scene);
        sb.append(", readed=").append(readed);
        sb.append(", speaker=").append(speaker);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}