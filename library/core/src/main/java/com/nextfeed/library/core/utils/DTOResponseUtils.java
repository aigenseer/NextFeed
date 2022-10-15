package com.nextfeed.library.core.utils;

import com.nextfeed.library.core.proto.response.Response;

public class DTOResponseUtils {

    public static Response.IDResponse createIDResponse(Integer id){
        return Response.IDResponse.newBuilder().setId(id).build();
    }

    public static Response.Empty createEmpty(){
        return Response.Empty.newBuilder().build();
    }

    public static Response.BooleanResponse createBooleanResponse(boolean b){
        return Response.BooleanResponse.newBuilder().setResult(b).build();
    }

}
