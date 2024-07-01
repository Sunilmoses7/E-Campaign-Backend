package com.payoman.campaign.util;

import com.payoman.campaign.model.OldElectionResults;
import com.payoman.campaign.model.Voter;
import com.payoman.campaign.service.ReportService;
import com.payoman.campaign.service.VoterService;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
public class FileUtil {

    @Autowired
    private VoterService service;

    @Autowired
    private ReportService reportService;


    @Async
    public List<Voter> parseExcel(InputStream is) {
        log.info("Parsing Excel");
        List<Voter> voterList = new ArrayList<>();
        try (ReadableWorkbook wb = new ReadableWorkbook(is)) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream().skip(1)) {
                rows.forEach(row -> {
                    Voter voter = new Voter();
                    voter.setACNo(row.getCellRawValue(0).orElse(""));
                    voter.setPartNo(row.getCellRawValue(1).orElse(""));
                    voter.setSectionNo(row.getCellRawValue(2).orElse(""));
                    voter.setSNo(row.getCellRawValue(3).orElse(""));
                    voter.setHouseNoEn(row.getCellRawValue(4).orElse(""));
                    voter.setHouseNo(row.getCellRawValue(5).orElse(""));
                    voter.setVoterNameEn(row.getCellRawValue(6).orElse(""));
                    voter.setVoterNameKan(row.getCellRawValue(7).orElse(""));
                    voter.setSex(row.getCellRawValue(8).orElse(""));
                    voter.setRelationNameEn(row.getCellRawValue(9).orElse(""));
                    voter.setRelationNameKan(row.getCellRawValue(10).orElse(""));
                    voter.setRelationType(row.getCellRawValue(11).orElse(""));
                    voter.setAge(row.getCellRawValue(12).orElse(""));
                    voter.setVoterId(row.getCellRawValue(13).orElse(""));
                    voter.setStatusType(row.getCellRawValue(14).orElse(""));
                    voter.setPoolingStation(row.getCellRawValue(15).orElse(""));
                    voter.setSectionNameEn(row.getCellRawValue(17).orElse(""));
                    voter.setSectionNameKan(row.getCellRawValue(18).orElse(""));
                    voter.setDistrictNo(row.getCellRawValue(19).orElse(""));
                    voter.setPincode(row.getCellRawValue(20).orElse(""));
                    voter.setMobileNo(row.getCellRawValue(21).orElse(""));
                    voter.setHouseHeadMobileNo(row.getCellRawValue(22).orElse(""));
                    voter.setHouseHeadNameAndMembers(row.getCellRawValue(23).orElse(""));
                    voter.setHouseHeadRelationship(row.getCellRawValue(24).orElse(""));
                    voter.setHouseHeadGender(row.getCellRawValue(25).orElse(""));
                    voter.setReligion(row.getCellRawValue(26).orElse(""));
                    voter.setCaste(row.getCellRawValue(27).orElse(""));
                    voter.setMotherTongue(row.getCellRawValue(28).orElse(""));
                    voter.setElectionCommissionIdentityCardNo(row.getCellRawValue(29).orElse(""));
                    voter.setLatitude("");
                    voter.setLongitude("");
                    voterList.add(voter);
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.create(voterList);
        log.info("Voters data inserted ...");
        log.info("Original Data set size ---- " + voterList.size());
        return voterList;
    }

    @Async
    public void parseOldReport(InputStream inputStream) {
        log.info("Parsing Excel");
        List<OldElectionResults> oldElectionResults = new ArrayList<>();
        try (ReadableWorkbook wb = new ReadableWorkbook(inputStream)) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream().skip(1)) {
                rows.forEach(row -> {
                    if (!row.getCellRawValue(5).orElse("").toLowerCase().contains("total")) {
                        OldElectionResults electionResults = new OldElectionResults();
                        electionResults.setMpConstituencyNumber(row.getCellRawValue(0).orElse(""));
                        electionResults.setElectionType(row.getCellRawValue(1).orElse(""));
                        electionResults.setAssemblyConstituencyNumber(row.getCellRawValue(2).orElse(""));
                        electionResults.setAssemblyConstituencyName(row.getCellRawValue(3).orElse(""));
                        electionResults.setPartNo(row.getCellRawValue(4).orElse(""));
                        electionResults.setPartyName(row.getCellRawValue(5).orElse(""));
                        electionResults.setCandidateName(row.getCellRawValue(6).orElse(""));
                        electionResults.setElectionYear(row.getCellRawValue(7).orElse(""));
                        electionResults.setNumberOfVotes(Integer.parseInt(row.getCellRawValue(8).orElse("0")));
                        electionResults.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        oldElectionResults.add(electionResults);
                    }
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reportService.insertOldReports(oldElectionResults);
        log.info("Voters data inserted ...");
        log.info("Original Data set size ---- " + oldElectionResults.size());
    }
}