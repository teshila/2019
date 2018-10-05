package com.ly.webservice.impl;

import java.util.Calendar;
import javax.jws.WebService;
import com.ly.webservice.Greeting;


//本文https://www.cnblogs.com/moon521/p/5564504.html?tdsourcetag=s_pcqq_aiomsg
//https://www.cnblogs.com/foxting/p/6940258.html
//https://www.cnblogs.com/moon521/p/5564504.html?tdsourcetag=s_pcqq_aiomsg
//https://blog.csdn.net/tzdwsy/article/details/51938786
//https://blog.csdn.net/csolo/article/details/54839227

@WebService(endpointInterface="com.ly.webservice.Greeting")
public class GreetingImpl implements Greeting{

    public String greeting(String userName) {
        return "Hello " + userName + ", currentTime is "
                + Calendar.getInstance().getTime();
    }

}