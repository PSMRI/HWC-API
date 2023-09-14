package com.iemr.hwc.data.login;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.data.location.DistrictBranchMapping;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_usermastervillagemapping")
public class UsersMasterVillage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    @Column(name = "id")
    private Long id;
    @Expose
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;
    @Expose
    @ManyToOne
    @JoinColumn(name="masterVillage_id")
    private DistrictBranchMapping masterVillage;
    @Expose
    @Column(name = "CreatedDate")
    private Timestamp createdDate;
    @Expose
    @Column(name = "LastModDate")
    private Timestamp lastModDate;
    @Expose
    @Column(name = "active")
    private Boolean active;

}