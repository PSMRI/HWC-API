package com.iemr.hwc.data.anc;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "t_anccare", schema = "db_iemr")
public class AncCare {
    private long id;
    private Long beneficiaryRegId;
    private Long benVisitId;
    private Short visitNo;
    private String comolaintType;
    private String duration;
    private String description;
    private Timestamp lastMenstrualPeriodLmp;
    private Short gestationalAgeOrPeriodofAmenorrheaPoa;
    private Timestamp expectedDateofDelivery;
    private Short trimesterNumber;
    private Boolean primiGravida;
    private Short gravidaG;
    private Short termDeliveriesT;
    private Short pretermDeliveriesP;
    private Short abortionsA;
    private Short livebirthsL;
    private String bloodGroup;
    private Short stillBirth;
    private Short paraP;
    private Boolean deleted;
    private String processed;
    private String createdBy;
    private Timestamp createdDate;
    private String modifiedBy;
    private Timestamp lastModDate;
    private Long vanSerialNo;
    private Integer vanId;
    private String vehicalNo;
    private Integer parkingPlaceId;
    private String syncedBy;
    private Timestamp syncedDate;
    private String reservedForChange;

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BeneficiaryRegID")
    public Long getBeneficiaryRegId() {
        return beneficiaryRegId;
    }

    public void setBeneficiaryRegId(Long beneficiaryRegId) {
        this.beneficiaryRegId = beneficiaryRegId;
    }

    @Basic
    @Column(name = "BenVisitID")
    public Long getBenVisitId() {
        return benVisitId;
    }

    public void setBenVisitId(Long benVisitId) {
        this.benVisitId = benVisitId;
    }

    @Basic
    @Column(name = "VisitNo")
    public Short getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(Short visitNo) {
        this.visitNo = visitNo;
    }

    @Basic
    @Column(name = "ComolaintType")
    public String getComolaintType() {
        return comolaintType;
    }

    public void setComolaintType(String comolaintType) {
        this.comolaintType = comolaintType;
    }

    @Basic
    @Column(name = "Duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "LastMenstrualPeriod_LMP")
    public Timestamp getLastMenstrualPeriodLmp() {
        return lastMenstrualPeriodLmp;
    }

    public void setLastMenstrualPeriodLmp(Timestamp lastMenstrualPeriodLmp) {
        this.lastMenstrualPeriodLmp = lastMenstrualPeriodLmp;
    }

    @Basic
    @Column(name = "GestationalAgeOrPeriodofAmenorrhea_POA")
    public Short getGestationalAgeOrPeriodofAmenorrheaPoa() {
        return gestationalAgeOrPeriodofAmenorrheaPoa;
    }

    public void setGestationalAgeOrPeriodofAmenorrheaPoa(Short gestationalAgeOrPeriodofAmenorrheaPoa) {
        this.gestationalAgeOrPeriodofAmenorrheaPoa = gestationalAgeOrPeriodofAmenorrheaPoa;
    }

    @Basic
    @Column(name = "ExpectedDateofDelivery")
    public Timestamp getExpectedDateofDelivery() {
        return expectedDateofDelivery;
    }

    public void setExpectedDateofDelivery(Timestamp expectedDateofDelivery) {
        this.expectedDateofDelivery = expectedDateofDelivery;
    }

    @Basic
    @Column(name = "TrimesterNumber")
    public Short getTrimesterNumber() {
        return trimesterNumber;
    }

    public void setTrimesterNumber(Short trimesterNumber) {
        this.trimesterNumber = trimesterNumber;
    }

    @Basic
    @Column(name = "PrimiGravida")
    public Boolean getPrimiGravida() {
        return primiGravida;
    }

    public void setPrimiGravida(Boolean primiGravida) {
        this.primiGravida = primiGravida;
    }

    @Basic
    @Column(name = "Gravida_G")
    public Short getGravidaG() {
        return gravidaG;
    }

    public void setGravidaG(Short gravidaG) {
        this.gravidaG = gravidaG;
    }

    @Basic
    @Column(name = "TermDeliveries_T")
    public Short getTermDeliveriesT() {
        return termDeliveriesT;
    }

    public void setTermDeliveriesT(Short termDeliveriesT) {
        this.termDeliveriesT = termDeliveriesT;
    }

    @Basic
    @Column(name = "PretermDeliveries_P")
    public Short getPretermDeliveriesP() {
        return pretermDeliveriesP;
    }

    public void setPretermDeliveriesP(Short pretermDeliveriesP) {
        this.pretermDeliveriesP = pretermDeliveriesP;
    }

    @Basic
    @Column(name = "Abortions_A")
    public Short getAbortionsA() {
        return abortionsA;
    }

    public void setAbortionsA(Short abortionsA) {
        this.abortionsA = abortionsA;
    }

    @Basic
    @Column(name = "Livebirths_L")
    public Short getLivebirthsL() {
        return livebirthsL;
    }

    public void setLivebirthsL(Short livebirthsL) {
        this.livebirthsL = livebirthsL;
    }

    @Basic
    @Column(name = "BloodGroup")
    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Basic
    @Column(name = "stillBirth")
    public Short getStillBirth() {
        return stillBirth;
    }

    public void setStillBirth(Short stillBirth) {
        this.stillBirth = stillBirth;
    }

    @Basic
    @Column(name = "Para_P")
    public Short getParaP() {
        return paraP;
    }

    public void setParaP(Short paraP) {
        this.paraP = paraP;
    }

    @Basic
    @Column(name = "Deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "Processed")
    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    @Basic
    @Column(name = "CreatedBy")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "CreatedDate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "ModifiedBy")
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Basic
    @Column(name = "LastModDate")
    public Timestamp getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(Timestamp lastModDate) {
        this.lastModDate = lastModDate;
    }

    @Basic
    @Column(name = "VanSerialNo")
    public Long getVanSerialNo() {
        return vanSerialNo;
    }

    public void setVanSerialNo(Long vanSerialNo) {
        this.vanSerialNo = vanSerialNo;
    }

    @Basic
    @Column(name = "VanID")
    public Integer getVanId() {
        return vanId;
    }

    public void setVanId(Integer vanId) {
        this.vanId = vanId;
    }

    @Basic
    @Column(name = "VehicalNo")
    public String getVehicalNo() {
        return vehicalNo;
    }

    public void setVehicalNo(String vehicalNo) {
        this.vehicalNo = vehicalNo;
    }

    @Basic
    @Column(name = "ParkingPlaceID")
    public Integer getParkingPlaceId() {
        return parkingPlaceId;
    }

    public void setParkingPlaceId(Integer parkingPlaceId) {
        this.parkingPlaceId = parkingPlaceId;
    }

    @Basic
    @Column(name = "SyncedBy")
    public String getSyncedBy() {
        return syncedBy;
    }

    public void setSyncedBy(String syncedBy) {
        this.syncedBy = syncedBy;
    }

    @Basic
    @Column(name = "SyncedDate")
    public Timestamp getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Timestamp syncedDate) {
        this.syncedDate = syncedDate;
    }

    @Basic
    @Column(name = "ReservedForChange")
    public String getReservedForChange() {
        return reservedForChange;
    }

    public void setReservedForChange(String reservedForChange) {
        this.reservedForChange = reservedForChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AncCare tAnccare = (AncCare) o;
        return id == tAnccare.id &&
                Objects.equals(beneficiaryRegId, tAnccare.beneficiaryRegId) &&
                Objects.equals(benVisitId, tAnccare.benVisitId) &&
                Objects.equals(visitNo, tAnccare.visitNo) &&
                Objects.equals(comolaintType, tAnccare.comolaintType) &&
                Objects.equals(duration, tAnccare.duration) &&
                Objects.equals(description, tAnccare.description) &&
                Objects.equals(lastMenstrualPeriodLmp, tAnccare.lastMenstrualPeriodLmp) &&
                Objects.equals(gestationalAgeOrPeriodofAmenorrheaPoa, tAnccare.gestationalAgeOrPeriodofAmenorrheaPoa) &&
                Objects.equals(expectedDateofDelivery, tAnccare.expectedDateofDelivery) &&
                Objects.equals(trimesterNumber, tAnccare.trimesterNumber) &&
                Objects.equals(primiGravida, tAnccare.primiGravida) &&
                Objects.equals(gravidaG, tAnccare.gravidaG) &&
                Objects.equals(termDeliveriesT, tAnccare.termDeliveriesT) &&
                Objects.equals(pretermDeliveriesP, tAnccare.pretermDeliveriesP) &&
                Objects.equals(abortionsA, tAnccare.abortionsA) &&
                Objects.equals(livebirthsL, tAnccare.livebirthsL) &&
                Objects.equals(bloodGroup, tAnccare.bloodGroup) &&
                Objects.equals(stillBirth, tAnccare.stillBirth) &&
                Objects.equals(paraP, tAnccare.paraP) &&
                Objects.equals(deleted, tAnccare.deleted) &&
                Objects.equals(processed, tAnccare.processed) &&
                Objects.equals(createdBy, tAnccare.createdBy) &&
                Objects.equals(createdDate, tAnccare.createdDate) &&
                Objects.equals(modifiedBy, tAnccare.modifiedBy) &&
                Objects.equals(lastModDate, tAnccare.lastModDate) &&
                Objects.equals(vanSerialNo, tAnccare.vanSerialNo) &&
                Objects.equals(vanId, tAnccare.vanId) &&
                Objects.equals(vehicalNo, tAnccare.vehicalNo) &&
                Objects.equals(parkingPlaceId, tAnccare.parkingPlaceId) &&
                Objects.equals(syncedBy, tAnccare.syncedBy) &&
                Objects.equals(syncedDate, tAnccare.syncedDate) &&
                Objects.equals(reservedForChange, tAnccare.reservedForChange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beneficiaryRegId, benVisitId, visitNo, comolaintType, duration, description, lastMenstrualPeriodLmp, gestationalAgeOrPeriodofAmenorrheaPoa, expectedDateofDelivery, trimesterNumber, primiGravida, gravidaG, termDeliveriesT, pretermDeliveriesP, abortionsA, livebirthsL, bloodGroup, stillBirth, paraP, deleted, processed, createdBy, createdDate, modifiedBy, lastModDate, vanSerialNo, vanId, vehicalNo, parkingPlaceId, syncedBy, syncedDate, reservedForChange);
    }
}
