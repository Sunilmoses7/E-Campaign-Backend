package com.payoman.campaign.service.impl;

import com.payoman.campaign.model.Candidate;
import com.payoman.campaign.repo.CandidateRepo;
import com.payoman.campaign.service.CandidateService;
import com.payoman.campaign.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepo candidateRepo;

    @Override
    public List<Candidate> create(ArrayList<Candidate> candidates) {
        log.info("Creating candidate ...");
        try {
            return candidateRepo.saveAllAndFlush(candidates);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public Candidate getCandidateDetails(String assemblyConstituencyNumber, Integer partyId) {
        log.info("Fetching candidate details ...");
        return candidateRepo.findByAssemblyConstituencyNumberAndPartyId(assemblyConstituencyNumber, partyId);
    }

    @Override
    public Map<String, Object> updateCandidate(String candidatePhone, String documentType, String documentName, String url) {
        log.info("Updating candidate details ...");
        Map<String, Object> response = new HashMap<>();
        Candidate candidate = candidateRepo.findByCandidatePhoneNumber(candidatePhone);

        if (candidate != null) {
            switch (documentType) {
                case "CANDIDATE_IMAGE":
                    candidate.setCandidateImageUrl(url);
                    candidateRepo.save(candidate);
                    log.info(documentType + "Updated Successfully");
                    response.put("status", Constants.SUCCESS);
                    break;

                case "PARTY_SYMBOL":
                    candidate.setPartySymbolUrl(url);
                    candidateRepo.save(candidate);
                    log.info(documentType + "Updated Successfully");
                    response.put("status", Constants.SUCCESS);
                    break;

                case "CANDIDATE_MANIFESTO":
                    candidate.setCandidateManifestoUrl(url);
                    candidateRepo.save(candidate);
                    log.info(documentType + "Updated Successfully");
                    response.put("status", Constants.SUCCESS);
                    break;

                case "BANNER_URL":
                    ArrayList<String> urls;
                    if (candidate.getBannerUrls() == null)
                        urls = new ArrayList<>();
                    else
                        urls = candidate.getBannerUrls();

                    urls.add(url);
                    candidate.setBannerUrls(urls);
                    candidateRepo.save(candidate);
                    log.info(documentType + "Updated Successfully");
                    response.put("status", Constants.SUCCESS);
                    break;

                case "NEWSLETTER":
                    if (candidate.getNewsLetterUrls() == null)
                        urls = new ArrayList<>();
                    else
                        urls = candidate.getNewsLetterUrls();

                    urls.add(url);
                    candidate.setNewsLetterUrls(urls);
                    candidateRepo.save(candidate);
                    log.info(documentType + "Updated Successfully");
                    response.put("status", Constants.SUCCESS);
                    break;

                case "OTHER":
                    HashMap<String, String> urlMaps;
                    if (candidate.getUrls() == null) {
                        urlMaps = new HashMap<>();
                        urlMaps.put(documentName, url);
                    } else {
                        urlMaps = candidate.getUrls();
                        urlMaps.put(documentName, url);
                    }
                    candidate.setUrls(urlMaps);
                    candidateRepo.save(candidate);
                    log.info(documentType + "Updated Successfully");
                    response.put("status", Constants.SUCCESS);
                    break;

                default:
                    response.put("status", Constants.FAILED);
                    log.info("Wrong Document Type : " + documentType);
                    break;

            }
            return response;
        }
        return null;
    }

    @Override
    public boolean isCandidateAvailable(String phone) {
        log.info("Checking candidate availability ...");
        return candidateRepo.findByCandidatePhoneNumber(phone) != null;
    }
}
