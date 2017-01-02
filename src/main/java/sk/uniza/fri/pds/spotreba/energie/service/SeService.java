/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;

public interface SeService<T> {

    void update(T object);

    void create(T object);

    void delete(T object);

    //FIXME: Možno ešte nejaký find podla id, ale to bude treba pridať dalšie generikum na typ kluča. V prípade že pojde o kompozitný kľúč typom by mal byť nejaký pomocný object
    List<T> findAll();
}
