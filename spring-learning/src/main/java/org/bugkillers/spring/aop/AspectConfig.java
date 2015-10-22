package org.bugkillers.spring.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Created by liuxinyu on 15/10/22.
 * @version V1.0
 * @Title: AspectConfig.java
 * @Description: 该类等价于配置文件里的 <aop:aspectj-autoproxy proxy-target-class="false" />
 * @Company:meituan
 * @date 15/1/21 19:40
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
}
