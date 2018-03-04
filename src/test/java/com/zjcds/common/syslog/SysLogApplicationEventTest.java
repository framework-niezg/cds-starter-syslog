package com.zjcds.common.syslog;

import com.zjcds.common.syslog.domain.LogEvent;
import com.zjcds.common.syslog.domain.LogGroup;
import com.zjcds.common.syslog.domain.SysLogApplicationEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* SysLogApplicationEvent Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 1, 2018</pre> 
* @version 1.0 
*/ 
public class SysLogApplicationEventTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getLogEvent() 
* 
*/ 
@Test
public void test() throws Exception {
    SysLogApplicationEvent sysLogApplicationEvent = SysLogApplicationEvent.newBuilder(SysLogApplicationEvent.class).logEvent(LogEvent.newBuilder()
                                .name("test")
                                .logGroup(LogGroup.newBuilder().name("group").build())
                                .build())
            .operationUser("haha")
            .source(new Object())
            .build();
    Assert.assertNotNull(sysLogApplicationEvent);
}

}
