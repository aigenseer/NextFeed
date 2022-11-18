package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.SurveyTemplateManagerServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyTemplateManagerServiceClient {

    @GrpcClient("survey-service")
    private SurveyTemplateManagerServiceGrpc.SurveyTemplateManagerServiceBlockingStub rpcService;

    public DTOEntities.SurveyTemplateDTO createTemplate(DTOEntities.SurveyTemplateDTO dto) {
        return rpcService.createTemplate(dto);
    }

    public Optional<DTOEntities.SurveyTemplateDTO> getTemplateById(Integer id) {
        return Optional.of(rpcService.getTemplateById(DTORequestUtils.createIDRequest(id)).getSurveyTemplate());
    }

    public DTOEntities.SurveyTemplateDTOList getAllTemplates() {
        return rpcService.getAllTemplates(DTOResponseUtils.createEmpty());
    }

}
