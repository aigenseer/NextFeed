package com.nextfeed.service.core.mood.core.db;


import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.manager.repository.service.AbstractService;
import com.nextfeed.service.core.mood.ports.outgoing.MoodRepository;
import org.springframework.stereotype.Service;

@Service
public class MoodDBService extends AbstractService<MoodEntity, MoodRepository> {

    public MoodDBService(MoodRepository moodRepository) {
        super(moodRepository);
    }
}
