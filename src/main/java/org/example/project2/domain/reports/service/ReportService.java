package org.example.project2.domain.reports.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.item.exception.ItemIdIsInvalidException;
import org.example.project2.domain.item.repository.ItemRepository;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.reports.entity.Reports;
import org.example.project2.domain.reports.exception.DuplicateReportException;
import org.example.project2.domain.reports.repository.ReportsRepository;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportsRepository reportsRepository;
    private final ItemRepository itemRepository;

    public ResponseDTO<Void> postReport(PrincipalDetails principalDetails,
                                        Long itemId) {

        Member member = principalDetails.getMember();

        Items item = itemRepository.findById(itemId)
                .orElseThrow(ItemIdIsInvalidException::new);

        reportsRepository.findByMember_IdAndItemId(member.getId(), itemId)
                .ifPresent(r -> {
                    throw new DuplicateReportException();
                });

        item.updateReportCount();
        Reports reports = Reports.builder()
                .itemId(itemId)
                .member(member)
                .build();

        reportsRepository.save(reports);
        return ResponseDTO.ok();
    }
}

