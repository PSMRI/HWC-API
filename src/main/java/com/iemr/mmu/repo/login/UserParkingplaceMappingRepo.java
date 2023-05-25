package com.iemr.mmu.repo.login;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.login.UserParkingplaceMapping;

@Repository
@RestResource(exported = false)
public interface UserParkingplaceMappingRepo extends CrudRepository<UserParkingplaceMapping, Long> {
	@Query("SELECT x.parkingPlaceID,p.stateID,s.stateName,p.districtID,d.districtName,p.districtBlockID,b.blockName from UserParkingplaceMapping x "
			+ " INNER JOIN x.m_parkingplace p" + " INNER JOIN p.state s" + " INNER JOIN p.m_district d"
			+ " INNER JOIN p.districtBlock b" + " WHERE x.userID = :userID and x.deleted != 1 ")
	public List<Object[]> getUserParkingPlce(@Param("userID") Integer userID);

	public UserParkingplaceMapping findOneByUserIDAndProviderServiceMapIdAndDeleted(Integer userID, Integer providerServiceMapId,
			Integer deleted);

//	@Query("SELECT u from UserParkingplaceMapping u WHERE u.userID= :userID and u.providerServiceMapId =:providerServiceMapId and u.deleted =:deleted")
//	public List<Object[]> getUserParkingPlce1(@Param("userID") Integer userID,@Param("providerServiceMapId") Integer providerServiceMapId,@Param("deleted") Integer deleted );
//	

	// public UserParkingplaceMapping getUserParkingPlce1(Integer userid, Integer
	// providerServiceMapId);
}
