package lifecounter.radek.washington.edu.quizdroidparti;

import java.util.List;

/**
 * Created by apple on 5/12/15.
 * A repository interface that enables data retrieval between app/activities
 * and model.
 */

public interface TopicRepository {
    /**
     * Returns a list of topics.
     */
    public List<Topic> getTopicList();
}
