package com.payoman.campaign.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Voter {

    @Id
    private String VoterId;

    private String ACNo, partNo, SectionNo, SNo, HouseNoEn, HouseNo, VoterNameKan, VoterNameEn, Sex, RelationNameEn,
            RelationNameKan, RelationType, Age, StatusType, PoolingStation, SectionNameKan, SectionNameEn, DistrictNo, Pincode, MobileNo,
            HouseHeadMobileNo, HouseHeadNameAndMembers, HouseHeadRelationship, HouseHeadGender, Religion, Caste, MotherTongue, ElectionCommissionIdentityCardNo;

    private String latitude, longitude;
}