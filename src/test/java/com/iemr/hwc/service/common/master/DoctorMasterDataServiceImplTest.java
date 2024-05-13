package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.repo.masterrepo.anc.ServiceMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.InstituteRepo;
import com.iemr.hwc.repo.masterrepo.doctor.PreMalignantLesionMasterRepo;

@ExtendWith(MockitoExtension.class)
class DoctorMasterDataServiceImplTest {
    @InjectMocks
    private DoctorMasterDataServiceImpl doctorMasterDataServiceImpl;

    @Mock
    private InstituteRepo instituteRepo;

    @Mock
    private PreMalignantLesionMasterRepo preMalignantLesionMasterRepo;

    @Mock
    private ServiceMasterRepo serviceMasterRepo;

    @Test
    void testGetCancerScreeningMasterDataForDoctor() {
        // Arrange
        when(preMalignantLesionMasterRepo.getPreMalignantLesionMaster()).thenReturn(new ArrayList<>());
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForDoctor = doctorMasterDataServiceImpl
                .getCancerScreeningMasterDataForDoctor(1);

        // Assert
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(preMalignantLesionMasterRepo).getPreMalignantLesionMaster();
        assertEquals("{\"additionalServices\":[],\"higherHealthCare\":[],\"preMalignantLesionTypes\":[]}",
                actualCancerScreeningMasterDataForDoctor);
    }

    @Test
    void testGetCancerScreeningMasterDataForDoctor2() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(preMalignantLesionMasterRepo.getPreMalignantLesionMaster()).thenReturn(objectArrayList);
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForDoctor = doctorMasterDataServiceImpl
                .getCancerScreeningMasterDataForDoctor(1);

        // Assert
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(preMalignantLesionMasterRepo).getPreMalignantLesionMaster();
        assertEquals("{}", actualCancerScreeningMasterDataForDoctor);
    }

    @Test
    void testGetCancerScreeningMasterDataForDoctor3() {
        // Arrange
        when(preMalignantLesionMasterRepo.getPreMalignantLesionMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(objectArrayList);
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForDoctor = doctorMasterDataServiceImpl
                .getCancerScreeningMasterDataForDoctor(1);

        // Assert
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(preMalignantLesionMasterRepo).getPreMalignantLesionMaster();
        assertEquals("{\"preMalignantLesionTypes\":[]}", actualCancerScreeningMasterDataForDoctor);
    }

    @Test
    void testGetCancerScreeningMasterDataForDoctor4() {
        // Arrange
        when(preMalignantLesionMasterRepo.getPreMalignantLesionMaster()).thenReturn(new ArrayList<>());
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(objectArrayList);

        // Act
        String actualCancerScreeningMasterDataForDoctor = doctorMasterDataServiceImpl
                .getCancerScreeningMasterDataForDoctor(1);

        // Assert
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(preMalignantLesionMasterRepo).getPreMalignantLesionMaster();
        assertEquals("{\"higherHealthCare\":[],\"preMalignantLesionTypes\":[]}", actualCancerScreeningMasterDataForDoctor);
    }

    @Test
    void testGetCancerScreeningMasterDataForDoctor5() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{});
        when(preMalignantLesionMasterRepo.getPreMalignantLesionMaster()).thenReturn(objectArrayList);
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForDoctor = doctorMasterDataServiceImpl
                .getCancerScreeningMasterDataForDoctor(1);

        // Assert
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(preMalignantLesionMasterRepo).getPreMalignantLesionMaster();
        assertEquals("{}", actualCancerScreeningMasterDataForDoctor);
    }

    @Test
    void testGetCancerScreeningMasterDataForDoctor6() {
        // Arrange
        when(preMalignantLesionMasterRepo.getPreMalignantLesionMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{});
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(objectArrayList);
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForDoctor = doctorMasterDataServiceImpl
                .getCancerScreeningMasterDataForDoctor(1);

        // Assert
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(preMalignantLesionMasterRepo).getPreMalignantLesionMaster();
        assertEquals("{\"preMalignantLesionTypes\":[]}", actualCancerScreeningMasterDataForDoctor);
    }

    @Test
    void testGettersAndSetters() {
        DoctorMasterDataServiceImpl doctorMasterDataServiceImpl = new DoctorMasterDataServiceImpl();

        // Act
        doctorMasterDataServiceImpl.setInstituteRepo(mock(InstituteRepo.class));
        doctorMasterDataServiceImpl.setServiceMasterRepo(mock(ServiceMasterRepo.class));
    }
}
