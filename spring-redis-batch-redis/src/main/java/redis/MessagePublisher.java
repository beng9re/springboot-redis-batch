package redis;

import java.util.Map;

public interface MessagePublisher {

    public void publish(String message);
}
