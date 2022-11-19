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

@Service
public class SurveyTemplateManagerServiceClient {

    @GrpcClient("survey-service")
    private SurveyTemplateManagerServiceGrpc.SurveyTemplateManagerServiceBlockingStub rpcService;

    public SurveyTemplateValue createTemplate(DTOEntities.SurveyTemplateDTO dto) {
        return SurveyTemplateValue.createByDTO(rpcService.createTemplate(dto));
    }

    public OptionalSurveyTemplateValue getTemplateById(Integer id) {
        return OptionalSurveyTemplateValue.createByDTO(rpcService.getTemplateById(DTORequestUtils.createIDRequest(id)));
    }

    public SurveyTemplateValueList getAllTemplates() {
        return SurveyTemplateValueList.createByDTO(rpcService.getAllTemplates(DTOResponseUtils.createEmpty()));
    }

}
