package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentCreateDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContent create(BinaryContentCreateDto dto) {
        BinaryContent binaryContent = new BinaryContent(dto.fileName(), dto.contentType(), dto.size(), dto.bytes());
        return binaryContentRepository.save(binaryContent);
    }


    @Override
    public BinaryContent find(UUID id) {
        return binaryContentRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("[!] 파일이 없습니다."));
    }

    @Override
    public List<BinaryContent> findAllById(List<UUID> ids) {
        List<BinaryContent> binaryContentList = new ArrayList<>();
        for (UUID id : ids) {
            BinaryContent binaryContent = binaryContentRepository.findById(id)
                    .orElseThrow(()->new NoSuchElementException("[!] 파일이 없습니다."));
            binaryContentList.add(binaryContent);
        }
        return binaryContentList;
    }

    @Override
    public void delete(UUID id) {
        binaryContentRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("[!] 파일이 없습니다."));
        binaryContentRepository.deleteById(id);
    }
}
