package anand.learn.entity;

import jakarta.persistence.*;

@Entity
public class Aadhaar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long aadhaarNumber;
    String address;
    @OneToOne
    User user;

    //setter and getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(Long aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
