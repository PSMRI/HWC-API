package com.iemr.hwc.data.choApp;

import com.google.gson.annotations.Expose;
import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_user_activity_logs")
public class UserActivityLogs {

    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int logId;

    @Expose
    @Column(name = "UserId")
    private String userId;

    @Expose
    @Column(name = "UserName")
    private String userName;

    @Expose
    @Column(name = "LoginType")
    private String loginType;

    @Expose
    @Column(name = "LoginOption")
    private String option;

    @Expose
    @Column(name = "LogoutType")
    private String logoutType;

    @Expose
    @Column(name = "LoginTime")
    private Timestamp loginTimeStamp;

    @Expose
    @Column(name = "LogoutTime")
    private Timestamp logoutTimeStamp;

    @Expose
    @Column(name = "IsOutOfReach")
    private Boolean isOutOfReach;

    @Lob
    @Column(name = "UserImage", columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;

    @Transient
    private String userImage;

    @Expose
    @Column(name = "Latitude")
    private Double latitude ;

    @Expose
    @Column(name = "Longitude")
    private Double longitude;

}

