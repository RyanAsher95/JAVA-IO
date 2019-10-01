package DM19S1;

/**
 * @Project DM19S1
 * @Package DM19S1
 * @Author Ryan<ywan3120 @ uni.sydney.edu.au>
 * @Date 26/04/2019
 */

import java.util.HashMap;

/**
 * @Description: Construct and store the donator object
 **/
public class Donator {
    private String name;
    private String birthday;
    private String address;
    private String postcode;
    private String phone;
    private String donation;
    private String recipient;
    /**
     * @Description: Construct constructor
     **/
    public Donator(){}

    /**
     * @Author Ryan
     * @Description: Initialize the object with HashMap
     * @Param [map]
     * @return
     **/
    Donator(HashMap<String,String> map){
        this.name = map.get("name");
        this.birthday = map.get("birthday");
        this.phone = map.get("phone");
        this.postcode = map.get("postcode");
        this.address = map.get("address");
        this.donation = map.get("donation");
        this.recipient = map.get("recipient");
    }


    /**
     * @Author Ryan
     * @Description: Getters and Setters
     * @Param []
     * @return java.lang.String
     **/
    public String getName(){return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday(){return birthday;}

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress(){return address;}

    public void setAddress(String address){
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDonation() {
        return donation;
    }

    public void setDonation(String donation){
        this. donation = donation;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }




}

