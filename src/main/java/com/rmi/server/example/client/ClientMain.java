package com.rmi.server.example.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.rmi.server.example.shared.HelloMessage;
import com.rmi.server.example.shared.HelloResponse;
import com.rmi.server.example.shared.HelloService;

/**
 * @author 
 */
public class ClientMain {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String SERVICE_URL = "rmi://%s:1099/HelloService";
    
    public static void main(String[] args) {
        ClientMain client = new ClientMain();
//        client.run();
        client.runWithApi();
    }

    /*
    <bean id="helloService" class="org.springframework.remoting.rmi.RmiProxyFactoryBean">
        <property name="serviceUrl" value="rmi://localhost:1099/HelloService"/>
        <property name="serviceInterface" value="com.rmi.server.example.shared.HelloService"/>
    </bean>
     */
    public void runWithApi() {
    	RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
    	factory.setServiceInterface(HelloService.class);
    	factory.setServiceUrl(String.format(SERVICE_URL, "localhost"));
    	
    	TimeoutRmiClientSocketFactory registryClientSocketFactory = new  TimeoutRmiClientSocketFactory();
    	
    	factory.setRegistryClientSocketFactory(registryClientSocketFactory);
    	
    	factory.afterPropertiesSet();
    	HelloService service = (HelloService) factory.getObject();
    	
        HelloMessage message = new HelloMessage();
        logger.info("request to server:");
        message.setText("hello from client-Api");
        HelloResponse response = service.sayHello(message);
        logger.info("response from server:");
        logger.info(response.getText());
    }
    
    public void run() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/com/rmi/server/example/client/applicationContext.xml");
        HelloService service = (HelloService) ctx.getBean("helloService");
        
        HelloMessage message = new HelloMessage();
        logger.info("request to server:");
        message.setText("hello from client");
        HelloResponse response = service.sayHello(message);
        logger.info("response from server:");
        logger.info(response.getText());
    }
}
