package com.rafa.sevenTao.request;


import lombok.Data;

import java.util.Date;

@Data
public class CountPriceRequest {

    private  long roomId;
    private Date start;
    private Date end;
}
