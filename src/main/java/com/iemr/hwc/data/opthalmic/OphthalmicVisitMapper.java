package com.iemr.hwc.data.opthalmic;

public class OphthalmicVisitMapper {

    public static OphthalmicVisit toEntity(OphthalmicVisitDTO dto) {
        OphthalmicVisit entity = new OphthalmicVisit();

        entity.setVisitId(dto.getVisitId());
        entity.setPatientID(dto.getPatientID());
        entity.setBenVisitNo(dto.getBenVisitNo());

        entity.setIsDiabetic(dto.getIsDiabetic());
        entity.setScreeningPerformed(dto.getScreeningPerformed());

        entity.setVisualAcuityChartUsed(dto.getVisualAcuityChartUsed());
        entity.setDistVARight(dto.getDistVARight());
        entity.setDistVALeft(dto.getDistVALeft());
        entity.setNearVA(dto.getNearVA());

        entity.setCaseIdConditions(dto.getCaseIdConditions());

        entity.setCataractSymptoms(dto.getCataractSymptoms());
        entity.setGlaucomaSymptoms(dto.getGlaucomaSymptoms());
        entity.setDiabeticRetinopathySymptoms(dto.getDiabeticRetinopathySymptoms());
        entity.setPresbyopiaSymptoms(dto.getPresbyopiaSymptoms());

        entity.setTrachomaStatus(dto.getTrachomaStatus());
        entity.setCornealDiseaseType(dto.getCornealDiseaseType());

        entity.setVitaminADeficiency(dto.getVitaminADeficiency());

        entity.setInjuryType(dto.getInjuryType());
        entity.setForeignBodyRemoval(dto.getForeignBodyRemoval());
        entity.setChemicalExposure(dto.getChemicalExposure());

        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());

        return entity;
    }

    public static OphthalmicVisitDTO toDTO(OphthalmicVisit entity) {
        OphthalmicVisitDTO dto = new OphthalmicVisitDTO();

        dto.setVisitId(entity.getVisitId());
        dto.setPatientID(entity.getPatientID());
        dto.setBenVisitNo(entity.getBenVisitNo());

        dto.setIsDiabetic(entity.getIsDiabetic());
        dto.setScreeningPerformed(entity.getScreeningPerformed());

        dto.setVisualAcuityChartUsed(entity.getVisualAcuityChartUsed());
        dto.setDistVARight(entity.getDistVARight());
        dto.setDistVALeft(entity.getDistVALeft());
        dto.setNearVA(entity.getNearVA());

        dto.setCaseIdConditions(entity.getCaseIdConditions());

        dto.setCataractSymptoms(entity.getCataractSymptoms());
        dto.setGlaucomaSymptoms(entity.getGlaucomaSymptoms());
        dto.setDiabeticRetinopathySymptoms(entity.getDiabeticRetinopathySymptoms());
        dto.setPresbyopiaSymptoms(entity.getPresbyopiaSymptoms());

        dto.setTrachomaStatus(entity.getTrachomaStatus());
        dto.setCornealDiseaseType(entity.getCornealDiseaseType());

        dto.setVitaminADeficiency(entity.getVitaminADeficiency());

        dto.setInjuryType(entity.getInjuryType());
        dto.setForeignBodyRemoval(entity.getForeignBodyRemoval());
        dto.setChemicalExposure(entity.getChemicalExposure());

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }
}