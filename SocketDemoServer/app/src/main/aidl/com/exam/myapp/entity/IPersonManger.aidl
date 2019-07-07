// IPersonManger.aidl
package com.exam.myapp.entity;

// Declare any non-default types here with import statements
import com.exam.myapp.entity.Person;
interface IPersonManger {
    List<Person> getPersons();
    void addPerson(in Person person);
}
