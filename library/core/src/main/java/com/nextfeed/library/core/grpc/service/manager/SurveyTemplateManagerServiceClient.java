package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.SurveyTemplateManagerServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.valueobject.surveytemplate.OptionalSurveyTemplateValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValueList;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyTemplateManagerServiceClient {

    @GrpcClient("survey-service")
    private SurveyTemplateManagerServiceGrpc.SurveyTemplateManagerServiceBlockingStub rpcService;

    public SurveyTemplateValue createTemplate(DTOEntities.SurveyTemplateDTO dto) {
        return SurveyTemplateValue.DTOBuilder().dto(rpcService.createTemplate(dto)).build();
    }

    public OptionalSurveyTemplateValue getTemplateById(Integer id) {
        return OptionalSurveyTemplateValue.DTOBuilder().dto(rpcService.getTemplateById(DTORequestUtils.createIDRequest(id))).build();
    }

    public SurveyTemplateValueList getAllTemplates() {
        return SurveyTemplateValueList.DTOBuilder().dto(rpcService.getAllTemplates(DTOResponseUtils.createEmpty())).build();
    }

}
