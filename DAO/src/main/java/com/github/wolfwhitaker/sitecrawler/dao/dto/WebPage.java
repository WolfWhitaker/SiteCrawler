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