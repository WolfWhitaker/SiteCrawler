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

package com.github.wolfwhitaker.sitecrawler.mvc.exception;

 /** This class represents a general model exception. It should wrap any exception
  * which occurs in the model part of a program.
  *
  * @author WolfWhitaker
  */
public class ModelException extends RuntimeException {

    /* Private constants */

    private static final long serialVersionUID = 1L;

    /* Constructors */

    /**
     * Constructs ModelException with the given detail message.
     * @param message The detail message of this ModelException.
     */
    public ModelException(String message) {
        super(message);
    }

    /**
     * Constructs ModelException with the given cause.
     * @param cause The cause of this ModelException.
     */
    public ModelException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs ModelException with the given detail message and cause.
     * @param message The detail message of this ModelException.
     * @param cause The cause of this ModelException.
     */
    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

}