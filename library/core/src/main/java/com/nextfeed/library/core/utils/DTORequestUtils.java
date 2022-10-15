package com.nextfeed.library.core.utils;

import com.nextfeed.library.core.proto.requests.Requests;

public class DTORequestUtils {


    public static Requests.IDRequest createIDRequest(Integer id){
        return Requests.IDRequest.newBuilder().setId(id).build();
    }

    public static Requests.SearchRequest createSearchRequest(String value){
        return Requests.SearchRequest.newBuilder().setSearch(value).build();
    }

}
