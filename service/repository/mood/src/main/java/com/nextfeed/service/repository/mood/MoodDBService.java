package com.nextfeed.service.repository.mood;


import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.manager.repository.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class MoodDBService extends AbstractService<MoodEntity, MoodRepository> {

    public MoodDBService(MoodRepository moodRepository) {
        super(moodRepository);
    }
}
