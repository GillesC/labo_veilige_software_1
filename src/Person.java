/**
 * Created by Gilles Callebaut on 4/02/2016.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable{
    private static final long serialVersionUID = 1;

    public Person(){};
    public Person(String name, String placeOfBirth, String phone){
        this.name = name;
        this.placeOfBirth = placeOfBirth;
        this.phone = phone;
    }

    private String name;
    private String placeOfBirth;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes(){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            return baos.toByteArray();
        }catch (IOException ioe){
            System.err.println(ioe.getLocalizedMessage());
            return null;
        }

    }

    public static Person getInstance(byte[] sPerson){
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(sPerson);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object o = ois.readObject();
            if(o instanceof Person)
                return (Person) o;
            else return null;
        }catch  (IOException ioe){
            System.err.println(ioe.getLocalizedMessage());
            return null;
        }catch (ClassNotFoundException nfe){
            System.err.println(nfe.getLocalizedMessage());
            return null;
        }
    }

    public String toString()
    {
        String stringPerson = "-----------------------------------------------\n";
        stringPerson+= ("UserName:       " + this.name + "\n");
        stringPerson+= ("Place of Birth: " + this.placeOfBirth + "\n");
        stringPerson+= ("Phone:          " + this.phone + "\n");
        stringPerson+= "-----------------------------------------------\n";
        return stringPerson;
    }

}
