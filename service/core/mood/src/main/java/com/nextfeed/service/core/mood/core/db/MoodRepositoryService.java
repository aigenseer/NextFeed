package com.nextfeed.service.core.mood.core.db;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.service.core.mood.ports.incoming.IMoodRepositoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoodRepositoryService implements IMoodRepositoryService {

    private final MoodDBService moodDBService;

    public DTOEntities.MoodEntityDTO save(DTOEntities.MoodEntityDTO dto) {
        var e = DTO2EntityUtils.dto2MoodEntity(dto);
        e = moodDBService.save(e);
        return Entity2DTOUtils.moodEntity2DTO(e);
    }

    public DTOEntities.MoodEntityDTOList findBySessionId(Requests.IDRequest request) {
        var list = moodDBService.getRepo().findBySessionId(request.getId());
        return DTOListUtils.moodEntities2DTO(list);
    }

    public void deleteAllBySessionId(Requests.IDRequest request) {
        moodDBService.getRepo().deleteAllBySessionId(request.getId());
    }

}
