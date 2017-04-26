package com.bonc.rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;


public class RabbitmqProConfig extends TimerTask {  
	
    @Override  
    public void run() {  
        ProductRecv productRecv = new ProductRecv();
        try {
			productRecv.productRe();
		} catch (ShutdownSignalException e) {
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }  
}  
