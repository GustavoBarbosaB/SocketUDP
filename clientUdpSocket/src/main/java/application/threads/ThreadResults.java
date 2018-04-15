package application.threads;

import java.util.logging.Logger;

public class ThreadResults extends Thread{

    //Todo substituir quando souber o resultado
    private String result;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ThreadResults(String result){
        this.result=result;
    }

    @Override
    public void run(){
        if(result.contains("1")||result.contains("2")) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.warning(e.getMessage());
            }
        }

        logger.info("O resultado eh: " + result);
    }

}
