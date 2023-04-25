/*
 * Author: Kevin Alvarado
 * Date: 11/8/2022
 * 
 * Description: This program uses a database with code, description, and price.
 * You can add a product of your choice, you can update it, you can delete it, or you can print it if you wish.
 */

package murach.db;

/* 
 * This is just a wrapper class so we can throw a common exception for
 * the UI to catch without tightly coupling the UI to the database layer.
 */
@SuppressWarnings("serial")
public class DBException extends Exception {
    DBException() {}
    
    DBException(Exception e) {
        super(e);
    }
}