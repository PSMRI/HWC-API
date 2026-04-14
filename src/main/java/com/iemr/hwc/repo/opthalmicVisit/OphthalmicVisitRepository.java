package com.iemr.hwc.repo.opthalmicVisit;

import com.iemr.hwc.data.opthalmic.OphthalmicVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RestResource(exported = false)
public interface OphthalmicVisitRepository extends JpaRepository<OphthalmicVisit, String> {
    List<OphthalmicVisit> findByCreatedBy(String createdBy);


}