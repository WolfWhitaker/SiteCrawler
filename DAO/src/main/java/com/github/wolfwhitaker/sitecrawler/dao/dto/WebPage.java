/*
 *
 ****************************************************************************
 *                                                                          *
 *                                SiteCrawler                               *
 *                                                                          *
 * is an open source project, which is distributed under "fair use" terms.  *
 * It means you can use any part of it's code as you wish and redistribute  *
 * it, but you should mention the author.                                   *
 *                                                                          *
 * WARNING!!! It is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR  *
 * CONDITIONS OF ANY KIND. You should also keep in mind that this project   *
 * uses the code of the third-party developers. So, if you want to use some *
 * part of it's code WHICH USES THIRD-PART LIBRARIES in your own project,   *
 * MAKE SURE that the way you use it doesn't violate THEIR TERMS OF USE.    *
 *                                                                          *
 * Copyright (C) 2018 WolfWhitaker                                          *
 *                                                                          *
 * My github page: https://github.com/WolfWhitaker                          *
 *                                                                          *
 ****************************************************************************
 *
 */

package com.github.wolfwhitaker.sitecrawler.dao.dto;

import java.io.Serializable;

/**
 * This class represents the WebPage DTO object. This DTO class can be used throughout
 * all levels of the program.
 *
 * @author WolfWhitaker
 */
public class WebPage implements Serializable {

    /* Private constants */

    private static final long serialVersionUID = 1L;

    /* Private variables */

    private Long id;
    private String content;
    private String plainText;

    /* Accessors */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    /* Actions */

    /**
     * The WebPage ID is unique for each web page. So this method compares WebPage ID only.
     * @see Object#equals(Object).
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof WebPage) && (id != null)
                ? id.equals(((WebPage) other).id)
                : (other == this);
    }

    /**
     * The WebPage ID is unique for each web page. So WebPage with the same ID returns the
     * same hashcode.
     * @see Object#hashCode().
     */
    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    /**
     * Returns the String representation of this WebPage object.
     * @see Object#toString().
     */
    @Override
    public String toString() {
        return String.format("WebPage[id=%d,content=%s]", id, content);
    }

}