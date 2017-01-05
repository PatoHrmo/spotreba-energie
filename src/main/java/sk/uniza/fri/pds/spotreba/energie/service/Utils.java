/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.sql.Date;

public class Utils {

    public static Date utilDateToSqlDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime());
    }
}
